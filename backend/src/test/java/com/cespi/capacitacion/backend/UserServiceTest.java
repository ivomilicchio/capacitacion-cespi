package com.cespi.capacitacion.backend;

import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.ResourceNotFoundException;
import com.cespi.capacitacion.backend.jwt.JwtService;
import com.cespi.capacitacion.backend.repository.ParkingSessionRepository;
import com.cespi.capacitacion.backend.repository.UserRepository;
import com.cespi.capacitacion.backend.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private ParkingSessionRepository parkingSessionRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testExistMail() {
        when(userRepository.findByMail("usuario@gmail.com"))
                .thenReturn(Optional.of(new User("2211234567", "usuario@gmail.com", "1234")));

        assertTrue(userService.existMail("usuario@gmail.com"));
        assertFalse(userService.existMail("usuario2@gmail.com"));
    }

    @Test
    public void testFindByPhoneNumber() {
        User user = new User("2211234567", "usuario@gmail.com", "1234");
        when(userRepository.findByPhoneNumber("2211234567"))
                .thenReturn(Optional.of(user));
        assertEquals(user, userService.findByPhoneNumber("2211234567"));
        assertThrows(ResourceNotFoundException.class, () -> userService.findByPhoneNumber("2210123456"));

    }

//    @Test
//    public void testHasSessionStarted() {
//        when(jwtService.getPhoneNumberFromToken("token")).thenReturn("2211234567");
//
//        User user = new User("2211234567", "usuario@gmail.com", "1234");
//        when(userRepository.findByPhoneNumber("2211234567"))
//                .thenReturn(Optional.of(user));
//
//        ParkingSession parkingSession = Mockito.mock(ParkingSession.class);
//        when(parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(1L))
//                .thenReturn(Optional.of(parkingSession));
//
//    }

}