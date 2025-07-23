package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.ParkingSessionResponse;
import com.cespi.capacitacion.backend.entity.CurrentAccount;
import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.AlreadyUsedNumberPlateException;
import com.cespi.capacitacion.backend.exception.HasSessionStartedException;
import com.cespi.capacitacion.backend.exception.OutOfServiceHourException;
import com.cespi.capacitacion.backend.jwt.JwtService;
import com.cespi.capacitacion.backend.repository.NumberPlateRepository;
import com.cespi.capacitacion.backend.repository.ParkingSessionRepository;
import com.cespi.capacitacion.backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Service
public class ParkingSessionServiceImpl implements ParkingSessionService {

    private final UserRepository userRepository;
    private final NumberPlateRepository numberPlateRepository;
    private final ParkingSessionRepository parkingSessionRepository;
    private final JwtService jwtService;
    private final UserService userService;

    @Value("${parking.start-time}")
    private String startTimeString;
    @Value("${parking.end-time}")
    private String endTimeString;
    @Value("${parking.price-per-hour}")
    private Double pricePerHour;

    private LocalTime startTime;
    private LocalTime endTime;

    public ParkingSessionServiceImpl(UserRepository userRepository, NumberPlateRepository numberPlateRepository,
                                     ParkingSessionRepository parkingSessionRepository, JwtService jwtService,
                                     UserService userService) {
        this.userRepository = userRepository;
        this.numberPlateRepository = numberPlateRepository;
        this.parkingSessionRepository = parkingSessionRepository;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        startTime = LocalTime.parse(startTimeString);
        endTime = LocalTime.parse(endTimeString);
    }

    @Transactional
    public ParkingSessionResponse startParkingSession(String authHeader, String number) {
        if (this.outOfServiceHour()) {
            throw new OutOfServiceHourException(this.startTimeString, this.endTimeString);
        }
        User user = userService.getUserFromAuthHeader(authHeader);
        CurrentAccount currentAccount = user.getCurrentAccount();
        if (this.hasSessionStarted(currentAccount.getId())) {
            throw new HasSessionStartedException();
        }
        NumberPlate numberPlate = numberPlateRepository.findByNumber(number).orElseThrow();
        if (parkingSessionRepository.findByNumberPlateIdAndEndTimeIsNull(numberPlate.getId()).isPresent()) {
            throw new AlreadyUsedNumberPlateException();
        }
        ParkingSession parkingSession = new ParkingSession(numberPlate, currentAccount);
        parkingSession = parkingSessionRepository.save(parkingSession);
        return new ParkingSessionResponse(parkingSession.getStartTime().toString());
    }

    @Transactional
    public Boolean finishParkingSession(String token) {
        String phoneNumber = jwtService.getPhoneNumberFromToken(token);
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        Long accountId = user.getCurrentAccount().getId();
        ParkingSession parkingSession = parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(accountId).orElseThrow();
        parkingSession.setEndTime(new Date());
        parkingSessionRepository.save(parkingSession);
        return true;
    }

    private boolean hasSessionStarted(Long accountId) {
        return parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(accountId).isPresent();
    }

    private boolean outOfServiceHour() {
        LocalTime now = LocalTime.now();
        return now.isBefore(startTime) || now.isAfter(endTime);
    }


}
