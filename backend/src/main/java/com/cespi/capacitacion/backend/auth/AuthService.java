package com.cespi.capacitacion.backend.auth;

import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.BadFormatPhoneNumberException;
import com.cespi.capacitacion.backend.exception.ExistMailException;
import com.cespi.capacitacion.backend.exception.ExistPhoneNumberException;
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

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, UserService userService, JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse login(LoginRequest request) {
        String sanitizedPhoneNumber = this.sanitizePhoneNumber(request.getPhoneNumber());
        this.validFormatOfPhoneNumber(sanitizedPhoneNumber);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sanitizedPhoneNumber,
                request.getPassword()));
        User user = userService.findByPhoneNumber(sanitizedPhoneNumber);
        return new AuthResponse(jwtService.getToken(user));
    }

    public AuthResponse register(RegisterRequest request) {
        String sanitizedPhoneNumber = this.sanitizePhoneNumber(request.getPhoneNumber());
        this.validFormatOfPhoneNumber(sanitizedPhoneNumber);
        checkIfExistPhoneNumber(sanitizedPhoneNumber);
        checkIfExistMail(request.getMail());
        User user = new User(request.getPhoneNumber(), request.getMail(), request.getPassword());
        userService.save(user);
        return new AuthResponse(jwtService.getToken(user));
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

    private void checkIfExistPhoneNumber(String phoneNumber) {
        if (userService.existPhoneNumber(phoneNumber)) {
            throw new ExistPhoneNumberException();
        }
    }

    private void checkIfExistMail(String mail) {
        if (userService.existMail(mail)) {
            throw new ExistMailException();
        }
    }
}
