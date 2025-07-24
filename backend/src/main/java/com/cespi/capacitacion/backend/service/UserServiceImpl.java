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
    public User save(String phoneNumber, String password) {
        String sanitizedPhoneNumber = sanitizePhoneNumber(phoneNumber);
        if (!validFormatOfPhoneNumber(sanitizedPhoneNumber)) {
            return null; //MANEJAR EXCEPCIONES ACA
        }
        User user = new User(sanitizedPhoneNumber, password);
        return userRepository.save(user);
    }


    @Transactional
    public ParkingSession hasSessionStarted(String token) {
        String phoneNumber = jwtService.getPhoneNumberFromToken(token);
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        Long accountId = user.getCurrentAccount().getId();
        System.out.println(parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(accountId));
        return parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(accountId).orElse(null);
    }

    private String sanitizePhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("[\\s-]", "");
    }

    private boolean validFormatOfPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("[0-9]{10}");
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }

    //ELIMINAR CUANDO NO HAYA MAS INVOCACIONES
    public User getUserFromToken(String token) {
        String phoneNumber = jwtService.getPhoneNumberFromToken(token);
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
                new ResourceNotFoundException("Usuario"));
    }

    public User getUserFromAuthHeader(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String phoneNumber = jwtService.getPhoneNumberFromToken(token);
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
                new ResourceNotFoundException("Usuario"));
    }

}
