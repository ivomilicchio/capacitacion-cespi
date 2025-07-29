package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.ResourceNotFoundException;
import com.cespi.capacitacion.backend.jwt.JwtService;
import com.cespi.capacitacion.backend.repository.ParkingSessionRepository;
import com.cespi.capacitacion.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public User getUserFromAuthHeader(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String phoneNumber = jwtService.getPhoneNumberFromToken(token);
        return this.findByPhoneNumber(phoneNumber);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()
                -> new ResourceNotFoundException("Usuario"));
    }

    public boolean existPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    public boolean existMail(String mail) {
        return userRepository.findByMail(mail).isPresent();
    }

}
