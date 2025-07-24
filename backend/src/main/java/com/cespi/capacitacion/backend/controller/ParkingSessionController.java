package com.cespi.capacitacion.backend.controller;

import com.cespi.capacitacion.backend.dto.ParkingSessionRequest;
import com.cespi.capacitacion.backend.dto.ParkingSessionResponse;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.service.ParkingSessionService;
import com.cespi.capacitacion.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking-sessions")
public class ParkingSessionController {

    private final ParkingSessionService parkingSessionService;
    private final UserService userService;

    public ParkingSessionController(ParkingSessionService parkingSessionService, UserService userService) {
        this.parkingSessionService = parkingSessionService;
        this.userService = userService;
    }

    @PostMapping
    public ParkingSessionResponse startParkingSession(@RequestBody ParkingSessionRequest parkingSessionRequest,
                                                      @RequestHeader("Authorization") String authHeader) {
        return parkingSessionService.startParkingSession(authHeader, parkingSessionRequest.getNumberPlate());

    }
    @GetMapping
    public ResponseEntity<Boolean> finishParkingSession(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(parkingSessionService.finishParkingSession(authHeader));
    }

    @GetMapping("/started")
    public ResponseEntity<ParkingSession> hasSessionStarted(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(userService.hasSessionStarted(token));
    }
}
