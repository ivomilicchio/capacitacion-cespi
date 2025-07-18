package com.cespi.capacitacion.backend.controller;

import com.cespi.capacitacion.backend.dto.UserCreationRequestDTO;
import com.cespi.capacitacion.backend.dto.UserRequestDTO;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public User save(@RequestBody UserCreationRequestDTO userCreationRequestDTO) {
        return userService.save(userCreationRequestDTO.getPhoneNumber(), userCreationRequestDTO.getPassword());
    }

    @GetMapping("/number-plates")
    public ResponseEntity<List<String>> getNumberPlatesOfUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(userService.getNumberPlatesOfUser(token));
    }

}
