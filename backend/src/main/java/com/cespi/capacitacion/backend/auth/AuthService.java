package com.cespi.capacitacion.backend.auth;

import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.BadFormatPhoneNumberException;
import com.cespi.capacitacion.backend.exception.ResourceNotFoundException;
import com.cespi.capacitacion.backend.jwt.JwtService;
import com.cespi.capacitacion.backend.repository.UserRepository;
import com.cespi.capacitacion.backend.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String sanitizedPhoneNumber = this.sanitizePhoneNumber(request.getPhoneNumber());
        this.validFormatOfPhoneNumber(sanitizedPhoneNumber);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sanitizedPhoneNumber,
                request.getPassword()));
        User user = this.findUserByPhoneNumber(sanitizedPhoneNumber);
        return new AuthResponse(jwtService.getToken(user));
    }

    public AuthResponse register(RegisterRequest request) {
        User user = new User(request.getPhoneNumber(), request.getPassword());
        userRepository.save(user);
        return new AuthResponse(jwtService.getToken(user));
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()
                -> new ResourceNotFoundException("Usuario"));
    }

    private String sanitizePhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("[\\s-]", "");
    }

    private void validFormatOfPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("[0-9]{10}");
        Matcher matcher = pattern.matcher(phoneNumber);

        if  (!matcher.matches()) {
            throw new BadFormatPhoneNumberException();
        }
    }
}
