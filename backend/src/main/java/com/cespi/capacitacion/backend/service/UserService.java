package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<ParkingSession> hasSessionStarted(String authHeader);
    User getUserFromAuthHeader(String authHeader);
    User save(User user);
    User findByPhoneNumber(String phoneNumber);
    User findById(Long id);
    boolean existPhoneNumber(String phoneNumber);
    boolean existMail(String mail);

}
