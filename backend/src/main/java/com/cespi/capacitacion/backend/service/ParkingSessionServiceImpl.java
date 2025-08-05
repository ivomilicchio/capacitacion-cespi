package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.ParkingSessionResponse;
import com.cespi.capacitacion.backend.entity.CurrentAccount;
import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.*;
import com.cespi.capacitacion.backend.repository.ParkingSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;

@Service
public class ParkingSessionServiceImpl implements ParkingSessionService {

    private final NumberPlateService numberPlateService;
    private final ParkingSessionRepository parkingSessionRepository;
    private final UserService userService;

    @Value("${parking.start-time}")
    private LocalTime startTime;
    @Value("${parking.end-time}")
    private LocalTime endTime;
    @Value("${parking.fraction-in-minutes}")
    private int fractionInMinutes;
    @Value("${parking.price-per-fraction}")
    private Double pricePerFraction;

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
        return new ParkingSessionResponse(parkingSession.getStartTime().toString());
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
        parkingSessionRepository.save(parkingSession);
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
}