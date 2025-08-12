package com.cespi.capacitacion.backend.auth;

import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.BadFormatPhoneNumberException;
import com.cespi.capacitacion.backend.exception.ExistMailException;
import com.cespi.capacitacion.backend.exception.ExistPhoneNumberException;
import com.cespi.capacitacion.backend.jwt.JwtService;
import com.cespi.capacitacion.backend.repository.UserRepository;
import com.cespi.capacitacion.backend.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cespi.capacitacion.backend.util.StringUtils.sanitizePhoneNumber;
import static com.cespi.capacitacion.backend.util.ValidationUtils.validFormatOfPhoneNumber;

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

    public AuthResponseDTO login(LoginRequestDTO request) {
        String sanitizedPhoneNumber = sanitizePhoneNumber(request.getPhoneNumber());
        validFormatOfPhoneNumber(sanitizedPhoneNumber);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sanitizedPhoneNumber,
                request.getPassword()));
        User user = userService.findByPhoneNumber(sanitizedPhoneNumber);
        return new AuthResponseDTO(jwtService.getToken(user));
    }

    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO request) {
        String sanitizedPhoneNumber = sanitizePhoneNumber(request.getPhoneNumber());
        validFormatOfPhoneNumber(sanitizedPhoneNumber);
        checkIfExistPhoneNumber(sanitizedPhoneNumber);
        checkIfExistMail(request.getMail());
        User user = new User(request.getPhoneNumber(), request.getMail(), request.getPassword());
        userService.save(user);
        return new AuthResponseDTO(jwtService.getToken(user));
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

    public User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return userService.findById(user.getId());
    }
}
