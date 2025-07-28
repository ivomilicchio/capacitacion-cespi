package com.cespi.capacitacion.backend.controller;

import com.cespi.capacitacion.backend.dto.ParkingSessionRequest;
import com.cespi.capacitacion.backend.dto.ParkingSessionResponse;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.service.ParkingSessionService;
import com.cespi.capacitacion.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<ParkingSessionResponse> startParkingSession(
            @RequestBody ParkingSessionRequest parkingSessionRequest,
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(parkingSessionService.startParkingSession(authHeader,
                parkingSessionRequest.getNumberPlate()));

    }
    @GetMapping
    public ResponseEntity<Void> finishParkingSession(@RequestHeader("Authorization") String authHeader) {
        parkingSessionService.finishParkingSession(authHeader);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/started")
    public ResponseEntity<ParkingSessionResponse> hasSessionStarted(@RequestHeader("Authorization") String authHeader) {
        Optional<ParkingSession> optionalParkingSession = userService.hasSessionStarted(authHeader);
        if (optionalParkingSession.isPresent()) {
            ParkingSession parkingSession = optionalParkingSession.get();
            ParkingSessionResponse response = new ParkingSessionResponse(parkingSession.getStartTime().toString());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.noContent().build();
    }
}
