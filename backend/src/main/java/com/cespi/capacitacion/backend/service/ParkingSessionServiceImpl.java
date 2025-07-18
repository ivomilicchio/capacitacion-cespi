package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.ParkingSessionResponse;
import com.cespi.capacitacion.backend.entity.CurrentAccount;
import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.jwt.JwtService;
import com.cespi.capacitacion.backend.repository.NumberPlateRepository;
import com.cespi.capacitacion.backend.repository.ParkingSessionRepository;
import com.cespi.capacitacion.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ParkingSessionServiceImpl implements ParkingSessionService {

    private final UserRepository userRepository;
    private final NumberPlateRepository numberPlateRepository;
    private final ParkingSessionRepository parkingSessionRepository;
    private final JwtService jwtService;

    public ParkingSessionServiceImpl(UserRepository userRepository, NumberPlateRepository numberPlateRepository,
                                     ParkingSessionRepository parkingSessionRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.numberPlateRepository = numberPlateRepository;
        this.parkingSessionRepository = parkingSessionRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public ParkingSessionResponse startParkingSession(String token, String number) {
        String phoneNumber = jwtService.getPhoneNumberFromToken(token);
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        CurrentAccount currentAccount = user.getCurrentAccount();
        NumberPlate numberPlate = numberPlateRepository.findByNumber(number).orElseThrow();
        ParkingSession parkingSession = new ParkingSession(numberPlate, currentAccount);
        parkingSession = parkingSessionRepository.save(parkingSession);
        return new ParkingSessionResponse(parkingSession.getStartTime().toString());
    }


}
