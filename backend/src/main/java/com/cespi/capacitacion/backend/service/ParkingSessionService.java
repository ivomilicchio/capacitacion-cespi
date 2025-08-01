package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.BalanceTopUpHistory;
import com.cespi.capacitacion.backend.dto.ParkingSessionHistory;
import com.cespi.capacitacion.backend.dto.ParkingSessionResponse;

public interface ParkingSessionService {

    ParkingSessionResponse startParkingSession(String authHeader, String numberPlate);
    void finishParkingSession(String authHeader);
    ParkingSessionHistory getParkingSessionHistory(String authHeader);

}
