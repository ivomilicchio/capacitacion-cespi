package com.cespi.capacitacion.backend.controller;

import com.cespi.capacitacion.backend.dto.UserCreationRequestDTO;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public User save(@RequestBody UserCreationRequestDTO userCreationRequestDTO) {
        return userService.save(userCreationRequestDTO.getPhoneNumber(), userCreationRequestDTO.getPassword());
    }
}
