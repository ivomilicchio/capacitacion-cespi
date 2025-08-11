package com.cespi.capacitacion.backend;

import com.cespi.capacitacion.backend.auth.AuthService;
import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.BadFormatNumberPlateException;
import com.cespi.capacitacion.backend.repository.NumberPlateRepository;
import com.cespi.capacitacion.backend.service.NumberPlateServiceImpl;
import com.cespi.capacitacion.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NumberPlateServiceTest {

    @Mock
    private NumberPlateRepository numberPlateRepository;

    @Mock
    private UserService userService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private NumberPlateServiceImpl numberPlateService;

    private User user;

    @BeforeEach
    public void setUp() {
        this.user = new User("2211234567", "usuario@gmail.com", "1234");
        when(authService.getUser()).thenReturn(user);
    }

    @Test
    public void testSaveNumberPlate() {

        NumberPlate numberPlate = new NumberPlate("ABC123");
        when(numberPlateRepository.findByNumber("ABC123")).thenReturn(Optional.of(numberPlate));

        NumberPlate numberPlate1 = new NumberPlate("AA123BB");
        when(numberPlateRepository.findByNumber("AA123BB")).thenReturn(Optional.of(numberPlate1));

        when(userService.save(user)).thenReturn(user);

        assertEquals("ABC123", numberPlateService.saveNumberPlate("abc-123").getNumber());

        assertEquals("AA123BB", numberPlateService.saveNumberPlate("aa 123 bb").getNumber());

        assertThrows(BadFormatNumberPlateException.class,
                () -> numberPlateService.saveNumberPlate("ABCD123"));
    }

    @Test
    public void testGetNumberPlatesOfUser() {

        NumberPlate numberPlate1 = new NumberPlate("AAA111");
        NumberPlate numberPlate2 = new NumberPlate("BBB222");
        NumberPlate numberPlate3 = new NumberPlate("CCC333");

        Set<String> numberPlateSet = new HashSet<>();
        numberPlateSet.add(numberPlate1.getNumber());
        numberPlateSet.add(numberPlate2.getNumber());
        numberPlateSet.add(numberPlate3.getNumber());

        user.addNumberPlate(numberPlate1);
        user.addNumberPlate(numberPlate2);
        user.addNumberPlate(numberPlate3);

        assertEquals(numberPlateSet, numberPlateService.getNumberPlatesOfUser().getNumberPlates());



    }



}
