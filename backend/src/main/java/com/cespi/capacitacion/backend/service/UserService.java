package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.User;

import java.util.List;

public interface UserService {

    User save(String phoneNumber, String password);
    ParkingSession hasSessionStarted(String token);
    User getUserFromToken(String token);
    User getUserFromAuthHeader(String authHeader);

}
