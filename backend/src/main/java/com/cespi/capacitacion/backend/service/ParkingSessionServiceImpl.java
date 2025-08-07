package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.ParkingSessionHistory;
import com.cespi.capacitacion.backend.dto.ParkingSessionResponse;
import com.cespi.capacitacion.backend.entity.*;
import com.cespi.capacitacion.backend.exception.*;
import com.cespi.capacitacion.backend.repository.ParkingSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class ParkingSessionServiceImpl implements ParkingSessionService {

    private final NumberPlateService numberPlateService;
    private final ParkingSessionRepository parkingSessionRepository;
    private final UserService userService;
    private final ClockService clockService;

    @Value("${parking.bussiness-days}")
    private List<String> bussinessDays;
    @Value("${parking.enable}")
    private boolean enable;
    @Value("${parking.start-time}")
    private LocalTime startTime;
    @Value("${parking.end-time}")
    private LocalTime endTime;
    @Value("${parking.fraction-in-minutes}")
    private int fractionInMinutes;
    @Value("${parking.price-per-fraction}")
    private Double pricePerFraction;

    public ParkingSessionServiceImpl(NumberPlateService numberPlateService,
                                     ParkingSessionRepository parkingSessionRepository, UserService userService,
                                     ClockService clockService) {
        this.numberPlateService = numberPlateService;
        this.parkingSessionRepository = parkingSessionRepository;
        this.userService = userService;
        this.clockService = clockService;
    }

    @Transactional
    public ParkingSessionResponse startParkingSession(String authHeader, String number) {
        this.checkOutOfService();
        User user = userService.getUserFromAuthHeader(authHeader);
        CurrentAccount currentAccount = user.getCurrentAccount();
        this.checkHasSessionStarted(currentAccount.getId());
        NumberPlate numberPlate = numberPlateService.findByNumber(number);
        this.checkAlreadyUsedNumberPlate(numberPlate.getId());
        this.checkInsufficientBalance(currentAccount);
        ParkingSession parkingSession = new ParkingSession(numberPlate, currentAccount);
        parkingSession = this.save(parkingSession);
        return new ParkingSessionResponse(parkingSession.getStarTimeDay(), parkingSession.getStarTimeHour());
    }

    private void checkOutOfService() {
        String day = clockService.getDayOfWeek();
        if (!bussinessDays.contains(day) || !this.enable) {
            throw new OutOfServiceException();
        }
        LocalTime now = clockService.getCurrentTime();
        if (now.isBefore(startTime) || now.isAfter(endTime)) {
            throw new OutOfServiceException(this.startTime.toString(), this.endTime.toString());
        }

    }

    private void checkHasSessionStarted(Long accountId) {
        if (parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(accountId).isPresent()) {
            throw new HasSessionStartedException();
        }
    }

    private void checkAlreadyUsedNumberPlate(Long numberPlateId) {
        if (parkingSessionRepository.findByNumberPlateIdAndEndTimeIsNull(numberPlateId).isPresent()) {
            throw new AlreadyUsedNumberPlateException();
        }
    }

    private void checkInsufficientBalance(CurrentAccount currentAccount) {

        if (currentAccount.getBalance() < this.calculateAmount(60)) {
            throw new InsufficientBalanceException();
        }
    }

    private ParkingSession save(ParkingSession parkingSession) {
        return parkingSessionRepository.save(parkingSession);
    }

    @Transactional
    public void finishParkingSession(String authHeader) {
        User user = userService.getUserFromAuthHeader(authHeader);
        CurrentAccount currentAccount = user.getCurrentAccount();
        ParkingSession parkingSession = this.getSessionStarted(currentAccount.getId());
        parkingSession.setEndTime(new Date());
        this.chargeService(currentAccount, parkingSession);
        this.save(parkingSession);
    }

    private ParkingSession getSessionStarted(Long accountId) {
        return parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(accountId).
                orElseThrow(NotSessionStartedException::new);
    }

    private void chargeService(CurrentAccount currentAccount, ParkingSession parkingSession) {
        Double amount = this.calculateAmount(parkingSession.getDurationInMinutes());
        Double balance = currentAccount.getBalance();
        currentAccount.setBalance(balance - amount);
        parkingSession.setAmount(amount);
    }

    private Double calculateAmount(long durationInMinutes) {
        long fractions = durationInMinutes / this.fractionInMinutes;
        if (durationInMinutes % this.fractionInMinutes > 0) {
            fractions++;
        }
        return fractions * this.pricePerFraction;
    }
    @Transactional
    public ParkingSessionHistory getParkingSessionHistory(String authHeader) {
        User user = this.userService.getUserFromAuthHeader(authHeader);
        List<ParkingSession> parkingSessions =  parkingSessionRepository.findAllByCurrentAccountIdAndEndTimeNotNull(
                user.getCurrentAccount().getId());
        return getHistory(parkingSessions);
    }

    private ParkingSessionHistory getHistory(List<ParkingSession> parkingSessions) {
        ParkingSessionHistory history = new ParkingSessionHistory();
        for (ParkingSession p: parkingSessions) {
            ParkingSessionResponse actual = new ParkingSessionResponse(p.getStarTimeDay(),
                    p.getStarTimeHour(), p.getEndTimeHour(), p.getAmount());
            history.addParkingSession(actual);
        }
        return history;
    }


}