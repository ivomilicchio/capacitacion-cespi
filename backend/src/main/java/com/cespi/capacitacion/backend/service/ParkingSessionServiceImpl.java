package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.ParkingSessionHistory;
import com.cespi.capacitacion.backend.dto.ParkingSessionResponse;
import com.cespi.capacitacion.backend.entity.*;
import com.cespi.capacitacion.backend.exception.*;
import com.cespi.capacitacion.backend.repository.ParkingSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class ParkingSessionServiceImpl implements ParkingSessionService {

    private final NumberPlateService numberPlateService;
    private final ParkingSessionRepository parkingSessionRepository;
    private final UserService userService;

    @Value("${parking.start-time}")
    private LocalTime startTime;
    @Value("${parking.end-time}")
    private LocalTime endTime;
    @Value("${parking.price-per-hour}")
    private Double pricePerHour;

    public ParkingSessionServiceImpl(NumberPlateService numberPlateService,
                                     ParkingSessionRepository parkingSessionRepository, UserService userService) {
        this.numberPlateService = numberPlateService;
        this.parkingSessionRepository = parkingSessionRepository;
        this.userService = userService;
    }

    @Transactional
    public ParkingSessionResponse startParkingSession(String authHeader, String number) {
        this.checkOutOfServiceHour();
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

    private void checkOutOfServiceHour() {
        LocalTime now = LocalTime.now();
        if (now.isBefore(startTime) || now.isAfter(endTime)) {
            throw new OutOfServiceHourException(this.startTime.toString(), this.endTime.toString());
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
        if (currentAccount.getBalance() < this.pricePerHour) {
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
        parkingSessionRepository.save(parkingSession);
    }

    private ParkingSession getSessionStarted(Long accountId) {
        return parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(accountId).
                orElseThrow(NotSessionStartedException::new);
    }

    private void chargeService(CurrentAccount currentAccount, ParkingSession parkingSession) {
        Double amount = parkingSession.getHours() * this.pricePerHour;
        Double balance = currentAccount.getBalance();
        currentAccount.setBalance(balance - amount);
        parkingSession.setAmount(amount);
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