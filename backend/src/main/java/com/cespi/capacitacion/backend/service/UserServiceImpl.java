package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.ResourceNotFoundException;
import com.cespi.capacitacion.backend.jwt.JwtService;
import com.cespi.capacitacion.backend.repository.ParkingSessionRepository;
import com.cespi.capacitacion.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ParkingSessionRepository parkingSessionRepository;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService,
                           ParkingSessionRepository parkingSessionRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.parkingSessionRepository = parkingSessionRepository;
    }


    @Transactional
    public Optional<ParkingSession> hasSessionStarted(String authHeader) {
        User user = this.getUserFromAuthHeader(authHeader);
        Long accountId = user.getCurrentAccount().getId();
        return parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(accountId);
    }

//
//
//    //ELIMINAR CUANDO NO HAYA MAS INVOCACIONES
//    public User getUserFromToken(String token) {
//        String phoneNumber = jwtService.getPhoneNumberFromToken(token);
//        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
//                new ResourceNotFoundException("Usuario"));
//    }

    public User getUserFromAuthHeader(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String phoneNumber = jwtService.getPhoneNumberFromToken(token);
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
                new ResourceNotFoundException("Usuario"));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
