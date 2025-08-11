package com.cespi.capacitacion.backend;

import com.cespi.capacitacion.backend.auth.AuthService;
import com.cespi.capacitacion.backend.dto.ParkingSessionHistoryDTO;
import com.cespi.capacitacion.backend.dto.ParkingSessionResponseDTO;
import com.cespi.capacitacion.backend.entity.CurrentAccount;
import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.*;
import com.cespi.capacitacion.backend.repository.ParkingSessionRepository;
import com.cespi.capacitacion.backend.service.ClockService;
import com.cespi.capacitacion.backend.service.NumberPlateService;
import com.cespi.capacitacion.backend.service.ParkingSessionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingSessionServiceTest {

    @Mock
    private NumberPlateService numberPlateService;

    @Mock
    private ParkingSessionRepository parkingSessionRepository;

    @Mock
    private AuthService authService;

    @Mock
    private ClockService clockService;

    @InjectMocks
    private ParkingSessionServiceImpl parkingSessionService;

    private User user;
    private CurrentAccount currentAccount;
    private NumberPlate numberPlate;

    @BeforeEach
    public void setUp() {

        this.user = new User("2211234567", "usuario@gmail.com", "1234");
        this.currentAccount = new CurrentAccount();
        currentAccount.setId(1L);
        user.setCurrentAccount(currentAccount);
        this.numberPlate = new NumberPlate("AAA123");
        numberPlate.setId(1L);
        user.addNumberPlate(numberPlate);

        List<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");

        ReflectionTestUtils.setField(parkingSessionService, "bussinessDays", days);
        ReflectionTestUtils.setField(parkingSessionService, "enable", true);
        ReflectionTestUtils.setField(parkingSessionService, "startTime", LocalTime.of(8, 0));
        ReflectionTestUtils.setField(parkingSessionService, "endTime", LocalTime.of(20, 0));

    }
    @Test
    public void testStartParkingSessionOutOfServiceDay() {

        when(clockService.getDayOfWeek()).thenReturn("Thursday");

        assertThrows(OutOfServiceException.class, () -> parkingSessionService.
                startParkingSession("AAA123"));
    }

    @Test
    public void testStartParkingSessionDisabled() {

        ReflectionTestUtils.setField(parkingSessionService, "enable", false);

        when(clockService.getDayOfWeek()).thenReturn("Monday");

        assertThrows(OutOfServiceException.class, () -> parkingSessionService.
                startParkingSession("AAA123"));
    }

    @Test
    public void testStartParkingSessionOutOfServiceHour() {

        when(clockService.getDayOfWeek()).thenReturn("Monday");
        when(clockService.getCurrentTime()).thenReturn(LocalTime.of(21, 0));

        assertThrows(OutOfServiceException.class, () -> parkingSessionService.
                startParkingSession("AAA123"));
    }

    @Test
    public void testStartParkingSessionWithSessionStarted() {

        when(clockService.getDayOfWeek()).thenReturn("Monday");
        when(clockService.getCurrentTime()).thenReturn(LocalTime.of(16, 0));
        when(authService.getUser()).thenReturn(user);

        ParkingSession parkingSession = new ParkingSession(numberPlate, currentAccount);

        when(parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(1L))
                .thenReturn(Optional.of(parkingSession));

        assertThrows(HasSessionStartedException.class, () -> parkingSessionService
                .startParkingSession("AAA123"));

    }

    @Test
    public void testStartParkingSessionWithAlreadyUsedNumberPlate() {

        when(clockService.getDayOfWeek()).thenReturn("Monday");
        when(clockService.getCurrentTime()).thenReturn(LocalTime.of(16, 0));
        when(authService.getUser()).thenReturn(user);
        when(parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(1L))
                .thenReturn(Optional.ofNullable(null));
        when(numberPlateService.findByNumber("AAA123")).thenReturn(numberPlate);

        CurrentAccount currentAccount1 = new CurrentAccount();
        ParkingSession parkingSession = new ParkingSession(numberPlate, currentAccount1);

        when(parkingSessionRepository.findByNumberPlateIdAndEndTimeIsNull(1L)).thenReturn(Optional.of(parkingSession));

        assertThrows(AlreadyUsedNumberPlateException.class,
                () -> parkingSessionService.startParkingSession("AAA123"));

    }

    @Test
    public void testStartParkingSessionWithInsufficientBalance() {

        when(clockService.getDayOfWeek()).thenReturn("Monday");
        when(clockService.getCurrentTime()).thenReturn(LocalTime.of(16, 0));
        when(authService.getUser()).thenReturn(user);
        when(parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(1L))
                .thenReturn(Optional.ofNullable(null));
        when(numberPlateService.findByNumber("AAA123")).thenReturn(numberPlate);

        when(parkingSessionRepository.findByNumberPlateIdAndEndTimeIsNull(1L))
                .thenReturn(Optional.ofNullable(null));

        currentAccount.setBalance(BigDecimal.valueOf(10));
        ReflectionTestUtils.setField(parkingSessionService, "fractionInMinutes", 15);
        ReflectionTestUtils.setField(parkingSessionService, "pricePerFraction", 20.0);

        assertThrows(InsufficientBalanceException.class,
                () -> parkingSessionService.startParkingSession("AAA123"));

    }

    @Test
    public void testStartParkingSession() {

        when(clockService.getDayOfWeek()).thenReturn("Monday");
        when(clockService.getCurrentTime()).thenReturn(LocalTime.of(16, 0));
        when(authService.getUser()).thenReturn(user);
        when(parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(1L))
                .thenReturn(Optional.ofNullable(null));
        when(numberPlateService.findByNumber("AAA123")).thenReturn(numberPlate);

        when(parkingSessionRepository.findByNumberPlateIdAndEndTimeIsNull(1L))
                .thenReturn(Optional.ofNullable(null));

        currentAccount.setBalance(BigDecimal.valueOf(1000));
        ReflectionTestUtils.setField(parkingSessionService, "fractionInMinutes", 15);
        ReflectionTestUtils.setField(parkingSessionService, "pricePerFraction", 20.0);

        ParkingSession parkingSession = new ParkingSession(numberPlate, currentAccount);

        when(parkingSessionRepository.save(any(ParkingSession.class))).thenReturn(parkingSession);

        ParkingSessionResponseDTO parkingSessionResponseDTO = parkingSessionService
                .startParkingSession("AAA123");

        assertEquals(parkingSession.getStartTimeDay(), parkingSessionResponseDTO.getDay());
        assertEquals(parkingSession.getStartTimeHour(), parkingSessionResponseDTO.getStartHour());
    }


    @Test
    public void testFinishParkingSessionWithoutSessionStarted() {

        when(authService.getUser()).thenReturn(user);

        when(parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(1L))
                .thenReturn(Optional.ofNullable(null));

        assertThrows(NotSessionStartedException.class,
                () -> parkingSessionService.finishParkingSession());
    }


    @Test
    public void testFinishParkingSession() {

        when(authService.getUser()).thenReturn(user);

        ParkingSession parkingSession = new ParkingSession(numberPlate, currentAccount);

        currentAccount.setBalance(BigDecimal.valueOf(100));

        LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minusMinutes(30);
        Date date = Date.from(thirtyMinutesAgo.atZone(ZoneId.systemDefault()).toInstant());
        parkingSession.setStartTime(date);

        ReflectionTestUtils.setField(parkingSessionService, "fractionInMinutes", 15);
        ReflectionTestUtils.setField(parkingSessionService, "pricePerFraction", 30.0);

        when(parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(1L))
                .thenReturn(Optional.of(parkingSession));

        when(parkingSessionRepository.save(parkingSession)).thenReturn(parkingSession);

        parkingSessionService.finishParkingSession();

        assertNotNull(parkingSession.getEndTime());

        assertEquals(BigDecimal.valueOf(40), currentAccount.getBalance());

    }

    @Test
    public void testGetParkingSessionHistory() {

        when(authService.getUser()).thenReturn(user);

        List<ParkingSession> parkingSessions = new ArrayList<>();

        ParkingSession p1 = new ParkingSession(numberPlate, currentAccount);
        ParkingSession p2 = new ParkingSession(numberPlate, currentAccount);
        ParkingSession p3 = new ParkingSession(numberPlate, currentAccount);

        p1.setEndTime(new Date());
        p2.setEndTime(new Date());
        p3.setEndTime(new Date());

        p1.setAmount(BigDecimal.valueOf(100));
        p2.setAmount(BigDecimal.valueOf(680));
        p3.setAmount(BigDecimal.valueOf(2500));

        parkingSessions.add(p1);
        parkingSessions.add(p2);
        parkingSessions.add(p3);

        when(parkingSessionRepository.findAllByCurrentAccountIdAndEndTimeNotNull(1L)).thenReturn(parkingSessions);

        ParkingSessionHistoryDTO parkingSessionHistoryDTO = new ParkingSessionHistoryDTO();

        ParkingSessionResponseDTO pr1 = new ParkingSessionResponseDTO(p1.getStartTimeDay(), p1.getStartTimeHour(),
                p1.getEndTimeHour(), p1.getAmount().doubleValue());

        ParkingSessionResponseDTO pr2 = new ParkingSessionResponseDTO(p2.getStartTimeDay(), p2.getStartTimeHour(),
                p2.getEndTimeHour(), p2.getAmount().doubleValue());

        ParkingSessionResponseDTO pr3 = new ParkingSessionResponseDTO(p3.getStartTimeDay(), p3.getStartTimeHour(),
                p3.getEndTimeHour(), p3.getAmount().doubleValue());

        parkingSessionHistoryDTO.addParkingSession(pr1);
        parkingSessionHistoryDTO.addParkingSession(pr2);
        parkingSessionHistoryDTO.addParkingSession(pr3);

        assertEquals(parkingSessionHistoryDTO, parkingSessionService.getParkingSessionHistory());

    }
}
