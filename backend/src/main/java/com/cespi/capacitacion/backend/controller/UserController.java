package com.cespi.capacitacion.backend.controller;

import com.cespi.capacitacion.backend.dto.UserCreationRequestDTO;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/parking-sessions/started")
    public ResponseEntity<ParkingSession> hasSessionStarted(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Optional<ParkingSession> parkingSession = userService.hasSessionStarted(token);
        if (parkingSession.isPresent()) {
            return ResponseEntity.ok(parkingSession.get());
        }
        return ResponseEntity.noContent().build();
    }

}
