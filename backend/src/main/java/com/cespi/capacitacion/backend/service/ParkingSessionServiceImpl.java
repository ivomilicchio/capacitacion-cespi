package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.ParkingSessionResponse;
import com.cespi.capacitacion.backend.entity.CurrentAccount;
import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.*;
import com.cespi.capacitacion.backend.repository.NumberPlateRepository;
import com.cespi.capacitacion.backend.repository.ParkingSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;

@Service
public class ParkingSessionServiceImpl implements ParkingSessionService {

    private final NumberPlateRepository numberPlateRepository;
    private final ParkingSessionRepository parkingSessionRepository;
    private final UserService userService;

    @Value("${parking.start-time}")
    private LocalTime startTime;
    @Value("${parking.end-time}")
    private LocalTime endTime;
    @Value("${parking.price-per-hour}")
    private Double pricePerHour;

    public ParkingSessionServiceImpl(NumberPlateRepository numberPlateRepository,
                                     ParkingSessionRepository parkingSessionRepository, UserService userService) {
        this.numberPlateRepository = numberPlateRepository;
        this.parkingSessionRepository = parkingSessionRepository;
        this.userService = userService;
    }

    @Transactional
    public ParkingSessionResponse startParkingSession(String authHeader, String number) {
        this.checkOutOfServiceHour();
        User user = userService.getUserFromAuthHeader(authHeader);
        CurrentAccount currentAccount = user.getCurrentAccount();
        this.checkHasSessionStarted(currentAccount.getId());
        NumberPlate numberPlate = this.findNumberPlateByNumber(number);
        this.checkAlreadyUsedNumberPlate(numberPlate.getId());
        this.checkInsufficientBalance(currentAccount);
        ParkingSession parkingSession = new ParkingSession(numberPlate, currentAccount);
        parkingSession = parkingSessionRepository.save(parkingSession);
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
        if (currentAccount.getBalance() < this.pricePerHour) {
            throw new InsufficientBalanceException();
        }
    }

    private NumberPlate findNumberPlateByNumber(String number) {
        return numberPlateRepository.findByNumber(number).orElseThrow(() -> new ResourceNotFoundException("Patente"));
    }

    @Transactional
    public Boolean finishParkingSession(String authHeader) {
        User user = userService.getUserFromAuthHeader(authHeader);
        CurrentAccount currentAccount = user.getCurrentAccount();
        ParkingSession parkingSession = this.getSessionStarted(currentAccount.getId());
        parkingSession.setEndTime(new Date());
        this.chargeService(currentAccount, parkingSession.getHours());
        parkingSessionRepository.save(parkingSession);
        return true;
    }

    private ParkingSession getSessionStarted(Long accountId) {
        return parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(accountId).orElseThrow(() ->
                new NotSessionStartedException());
    }

    private void chargeService(CurrentAccount currentAccount, long hours) {
        float balance = currentAccount.getBalance();
        currentAccount.setBalance((float) (balance - hours * this.pricePerHour));
    }
}