package com.cespi.capacitacion.backend.auth;

import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.jwt.JwtService;
import com.cespi.capacitacion.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public AuthResponse login(LoginRequest request) {
        return null;
    }

    public AuthResponse register(RegisterRequest request) {
        User user = new User(request.getPhoneNumber(), request.getPassword());
        userRepository.save(user);
        return new AuthResponse(jwtService.getToken(user));
    }
}
