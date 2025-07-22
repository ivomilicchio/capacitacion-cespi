package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.ParkingSessionResponse;

public interface ParkingSessionService {
    ParkingSessionResponse startParkingSession(String token, String numberPlate);

    Boolean finishParkingSession(String token);
}
