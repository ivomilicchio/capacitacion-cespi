package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.ParkingSessionResponse;
import com.cespi.capacitacion.backend.entity.CurrentAccount;
import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.User;
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

    public ParkingSessionServiceImpl(UserRepository userRepository, NumberPlateRepository numberPlateRepository,
                                     ParkingSessionRepository parkingSessionRepository) {
        this.userRepository = userRepository;
        this.numberPlateRepository = numberPlateRepository;
        this.parkingSessionRepository = parkingSessionRepository;
    }

    @Transactional
    public ParkingSessionResponse startParkingSession(String phoneNumber, String number) {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        CurrentAccount currentAccount = user.getCurrentAccount();
        NumberPlate numberPlate = numberPlateRepository.findByNumber(number).orElseThrow();
        ParkingSession parkingSession = new ParkingSession(numberPlate, currentAccount);
        parkingSession = parkingSessionRepository.save(parkingSession);
        return new ParkingSessionResponse(parkingSession.getStartTime().toString());
    }


}
