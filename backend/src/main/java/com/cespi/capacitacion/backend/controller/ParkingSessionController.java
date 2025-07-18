package com.cespi.capacitacion.backend.controller;

import com.cespi.capacitacion.backend.dto.ParkingSessionRequest;
import com.cespi.capacitacion.backend.dto.ParkingSessionResponse;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.service.ParkingSessionServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking-sessions")
public class ParkingSessionController {

    private final ParkingSessionServiceImpl parkingSessionService;

    public ParkingSessionController(ParkingSessionServiceImpl parkingSessionService) {
        this.parkingSessionService = parkingSessionService;
    }

    @PostMapping
    public ParkingSessionResponse startParkingSession(@RequestBody ParkingSessionRequest parkingSessionRequest,
                                                      @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return parkingSessionService.startParkingSession(token, parkingSessionRequest.getNumberPlate());

    }
}
