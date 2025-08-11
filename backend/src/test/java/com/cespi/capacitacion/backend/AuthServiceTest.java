package com.cespi.capacitacion.backend;

import com.cespi.capacitacion.backend.auth.AuthResponse;
import com.cespi.capacitacion.backend.auth.AuthService;
import com.cespi.capacitacion.backend.auth.LoginRequest;
import com.cespi.capacitacion.backend.auth.RegisterRequest;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.BadFormatPhoneNumberException;
import com.cespi.capacitacion.backend.exception.ExistMailException;
import com.cespi.capacitacion.backend.exception.ExistPhoneNumberException;
import com.cespi.capacitacion.backend.jwt.JwtService;
import com.cespi.capacitacion.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @Test
    public void testRegisterWithInvalidPhoneNumberFormat() {

        this.registerRequest = new RegisterRequest("2211234", "usuario@gmail.com", "1234");

        assertThrows(BadFormatPhoneNumberException.class, () -> authService.register(registerRequest));

    }

    @Test
    public void testRegisterWithAlreadyUsedPhoneNumber() {

        this.registerRequest = new RegisterRequest("2211234567", "usuario@gmail.com", "1234");

        when(userService.existPhoneNumber("2211234567")).thenReturn(true);

        assertThrows(ExistPhoneNumberException.class, () -> authService.register(registerRequest));
    }

    @Test
    public void testRegisterWithAlreadyUsedMail() {

        this.registerRequest = new RegisterRequest("2211234567", "usuario@gmail.com", "1234");

        when(userService.existPhoneNumber("2211234567")).thenReturn(false);
        when(userService.existMail("usuario@gmail.com")).thenReturn(true);

        assertThrows(ExistMailException.class, () -> authService.register(registerRequest));
    }

    @Test
    public void testLoginWithInvalidPhoneNumberFormat() {

        loginRequest = new LoginRequest("2211234", "1234");

        assertThrows(BadFormatPhoneNumberException.class, () -> authService.login(loginRequest));
    }

    @Test
    public void testLogin() {

        loginRequest = new LoginRequest("221 1234-567", "1234");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);

        User user = new User("2211234567", "usuario@gmail.com", "1234");

        when(userService.findByPhoneNumber("2211234567")).thenReturn(user);

        when(jwtService.getToken(user)).thenReturn("token");

        assertEquals("token", authService.login(loginRequest).getToken());
    }

}
