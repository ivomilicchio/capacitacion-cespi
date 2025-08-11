package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.ParkingSessionHistoryDTO;
import com.cespi.capacitacion.backend.dto.ParkingSessionResponseDTO;
import com.cespi.capacitacion.backend.entity.ParkingSession;

import java.util.Optional;

public interface ParkingSessionService {

    ParkingSessionResponseDTO startParkingSession(String numberPlate);
    void finishParkingSession();
    ParkingSessionHistoryDTO getParkingSessionHistory();
    Optional<ParkingSession> hasSessionStarted();

}
