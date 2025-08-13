package com.cespi.capacitacion.backend.controller;

import com.cespi.capacitacion.backend.dto.ParkingSessionHistoryDTO;
import com.cespi.capacitacion.backend.dto.ParkingSessionRequestDTO;
import com.cespi.capacitacion.backend.dto.ParkingSessionResponseDTO;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.service.ParkingSessionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/parking-sessions")
public class ParkingSessionController {

    private final ParkingSessionService parkingSessionService;

    public ParkingSessionController(ParkingSessionService parkingSessionService) {
        this.parkingSessionService = parkingSessionService;
    }

    @PostMapping
    public ResponseEntity<ParkingSessionResponseDTO> startParkingSession(
            @RequestBody @Valid ParkingSessionRequestDTO parkingSessionRequestDTO) {
        return ResponseEntity.ok(parkingSessionService.startParkingSession(parkingSessionRequestDTO.getNumberPlate()));

    }
    @GetMapping
    public ResponseEntity<Void> finishParkingSession() {
        parkingSessionService.finishParkingSession();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/started")
    public ResponseEntity<ParkingSessionResponseDTO> hasSessionStarted() {
        Optional<ParkingSession> optionalParkingSession = parkingSessionService.hasSessionStarted();
        if (optionalParkingSession.isPresent()) {
            ParkingSession parkingSession = optionalParkingSession.get();
            ParkingSessionResponseDTO response = new ParkingSessionResponseDTO(parkingSession.getStartTimeDay(),
                    parkingSession.getStartTimeHour());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/history")
    public ResponseEntity<ParkingSessionHistoryDTO> getParkingSessionHistory() {
        return ResponseEntity.ok(this.parkingSessionService.getParkingSessionHistory());
    }
}