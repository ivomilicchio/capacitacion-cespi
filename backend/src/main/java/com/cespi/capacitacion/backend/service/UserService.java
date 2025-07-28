package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<ParkingSession> hasSessionStarted(String authHeader);
    User getUserFromAuthHeader(String authHeader);
    User save(User user);


}
