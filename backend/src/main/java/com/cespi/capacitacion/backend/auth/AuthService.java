package com.cespi.capacitacion.backend.auth;

import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.jwt.JwtService;
import com.cespi.capacitacion.backend.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPhoneNumber(),
                request.getPassword()));
        UserDetails user = userRepository.findByPhoneNumber(request.getPhoneNumber()).orElseThrow();
        return new AuthResponse(jwtService.getToken(user));
    }

    public AuthResponse register(RegisterRequest request) {
        User user = new User(request.getPhoneNumber(), request.getPassword());
        userRepository.save(user);
        return new AuthResponse(jwtService.getToken(user));
    }
}
