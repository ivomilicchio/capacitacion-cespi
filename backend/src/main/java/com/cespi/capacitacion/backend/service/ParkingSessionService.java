package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.BalanceTopUpHistory;
import com.cespi.capacitacion.backend.dto.ParkingSessionHistory;
import com.cespi.capacitacion.backend.dto.ParkingSessionResponse;
import com.cespi.capacitacion.backend.entity.ParkingSession;

import java.util.Optional;

public interface ParkingSessionService {

    ParkingSessionResponse startParkingSession(String numberPlate);
    void finishParkingSession();
    ParkingSessionHistory getParkingSessionHistory();
    Optional<ParkingSession> hasSessionStarted();

}
