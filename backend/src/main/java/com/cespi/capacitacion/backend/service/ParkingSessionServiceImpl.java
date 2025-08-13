package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.auth.AuthService;
import com.cespi.capacitacion.backend.dto.ParkingSessionHistoryDTO;
import com.cespi.capacitacion.backend.dto.ParkingSessionResponseDTO;
import com.cespi.capacitacion.backend.entity.*;
import com.cespi.capacitacion.backend.exception.*;
import com.cespi.capacitacion.backend.repository.ParkingSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.cespi.capacitacion.backend.util.DateUtils.getCurrentTime;
import static com.cespi.capacitacion.backend.util.DateUtils.getDayOfWeek;

@Service
public class ParkingSessionServiceImpl implements ParkingSessionService {

    private final NumberPlateService numberPlateService;
    private final CurrentAccountService currentAccountService;
    private final ParkingSessionRepository parkingSessionRepository;
    private final AuthService authService;

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

    public ParkingSessionServiceImpl(NumberPlateService numberPlateService, CurrentAccountService currentAccountService,
                                     ParkingSessionRepository parkingSessionRepository, AuthService authService) {
        this.numberPlateService = numberPlateService;
        this.currentAccountService = currentAccountService;
        this.parkingSessionRepository = parkingSessionRepository;
        this.authService = authService;
    }


    public Optional<ParkingSession> hasSessionStarted() {
        User user = authService.getUser();
        Long accountId = user.getCurrentAccount().getId();
        return currentAccountService.findByCurrentAccountIdAndEndTimeIsNull(accountId);
    }

    @Transactional
    public ParkingSessionResponseDTO startParkingSession(String number) {
        this.checkOutOfService();
        User user = authService.getUser();
        CurrentAccount currentAccount = user.getCurrentAccount();
        this.checkHasSessionStarted(currentAccount.getId());
        NumberPlate numberPlate = numberPlateService.findByNumber(number);
        this.checkAlreadyUsedNumberPlate(numberPlate.getId());
        this.checkInsufficientBalance(currentAccount);
        ParkingSession parkingSession = new ParkingSession(numberPlate);
        currentAccount.addParkingSession(parkingSession);
        currentAccountService.save(currentAccount);
        return new ParkingSessionResponseDTO(parkingSession.getStartTimeDay(), parkingSession.getStartTimeHour());
    }

    private void checkOutOfService() {
        String day = getDayOfWeek();
        if (!bussinessDays.contains(day) || !this.enable) {
            throw new OutOfServiceException();
        }
        LocalTime now = getCurrentTime();
        if (now.isBefore(startTime) || now.isAfter(endTime)) {
            throw new OutOfServiceException(this.startTime.toString(), this.endTime.toString());
        }

    }

    private void checkHasSessionStarted(Long accountId) {
        if (currentAccountService.findByCurrentAccountIdAndEndTimeIsNull(accountId).isPresent()) {
            throw new HasSessionStartedException();
        }
    }

    private void checkAlreadyUsedNumberPlate(Long numberPlateId) {
        if (parkingSessionRepository.findByNumberPlateIdAndEndTimeIsNull(numberPlateId).isPresent()) {
            throw new AlreadyUsedNumberPlateException();
        }
    }

    private void checkInsufficientBalance(CurrentAccount currentAccount) {

        if (currentAccount.getBalance().compareTo(this.calculateAmount(60)) < 0) {
            throw new InsufficientBalanceException();
        }
    }

    private ParkingSession save(ParkingSession parkingSession) {
        return parkingSessionRepository.save(parkingSession);
    }

    @Transactional
    public void finishParkingSession() {
        User user = authService.getUser();
        CurrentAccount currentAccount = user.getCurrentAccount();
        ParkingSession parkingSession = this.getSessionStarted(currentAccount.getId());
        parkingSession.setEndTime(new Date());
        this.chargeService(currentAccount, parkingSession);
        this.save(parkingSession);
    }

    private ParkingSession getSessionStarted(Long accountId) {
        return currentAccountService.findByCurrentAccountIdAndEndTimeIsNull(accountId).
                orElseThrow(NotSessionStartedException::new);
    }

    private void chargeService(CurrentAccount currentAccount, ParkingSession parkingSession) {
        BigDecimal amount = this.calculateAmount(parkingSession.getDurationInMinutes());
        BigDecimal balance = currentAccount.getBalance();
        currentAccount.setBalance(balance.subtract(amount));
        parkingSession.setAmount(amount);
    }

    private BigDecimal calculateAmount(long durationInMinutes) {
        long fractions = durationInMinutes / this.fractionInMinutes;
        if (durationInMinutes % this.fractionInMinutes > 0) {
            fractions++;
        }
        return BigDecimal.valueOf(fractions * this.pricePerFraction);
    }

    public ParkingSessionHistoryDTO getParkingSessionHistory() {
        User user = authService.getUser();
        List<ParkingSession> parkingSessions =  currentAccountService.findAllByCurrentAccountIdAndEndTimeNotNull(
                user.getCurrentAccount().getId());
        return getHistory(parkingSessions);
    }

    private ParkingSessionHistoryDTO getHistory(List<ParkingSession> parkingSessions) {
        ParkingSessionHistoryDTO history = new ParkingSessionHistoryDTO();
        for (ParkingSession p: parkingSessions) {
            ParkingSessionResponseDTO actual = new ParkingSessionResponseDTO(p.getStartTimeDay(),
                    p.getStartTimeHour(), p.getEndTimeHour(), p.getAmount().doubleValue());
            history.addParkingSession(actual);
        }
        return history;
    }


}