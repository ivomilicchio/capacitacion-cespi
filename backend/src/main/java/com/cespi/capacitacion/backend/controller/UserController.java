package com.cespi.capacitacion.backend.controller;

import com.cespi.capacitacion.backend.dto.NumberPlateCreation;
import com.cespi.capacitacion.backend.dto.UserCreationRequestDTO;
import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping()
    public User save(@RequestBody UserCreationRequestDTO userCreationRequestDTO) {
        return userService.save(userCreationRequestDTO.getPhoneNumber(), userCreationRequestDTO.getPassword());
    }

    @GetMapping("/parking-sessions/started")
    public ResponseEntity<ParkingSession> hasSessionStarted(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(userService.hasSessionStarted(token));
    }

}
