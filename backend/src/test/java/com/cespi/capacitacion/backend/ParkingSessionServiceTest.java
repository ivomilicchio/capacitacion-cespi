package com.cespi.capacitacion.backend;

import com.cespi.capacitacion.backend.dto.ParkingSessionHistory;
import com.cespi.capacitacion.backend.dto.ParkingSessionResponse;
import com.cespi.capacitacion.backend.entity.CurrentAccount;
import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.OutOfServiceException;
import com.cespi.capacitacion.backend.repository.ParkingSessionRepository;
import com.cespi.capacitacion.backend.service.NumberPlateService;
import com.cespi.capacitacion.backend.service.ParkingSessionServiceImpl;
import com.cespi.capacitacion.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingSessionServiceTest {

    @Mock
    private NumberPlateService numberPlateService;

    @Mock
    private ParkingSessionRepository parkingSessionRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ParkingSessionServiceImpl parkingSessionService;

    private User user;
    private CurrentAccount currentAccount;

    @BeforeEach
    public void setUp() {
        this.user = new User("2211234567", "usuario@gmail.com", "1234");
        this.currentAccount = new CurrentAccount();
        currentAccount.setId(1L);
        user.setCurrentAccount(currentAccount);

        when(userService.getUserFromAuthHeader("authHeader")).thenReturn(user);
    }

    @Test
    public void testFinishParkingSession() {

        NumberPlate numberPlate = new NumberPlate("AAA123");
        ParkingSession parkingSession = new ParkingSession(numberPlate, currentAccount);

        currentAccount.setBalance(100.0);

        LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minusMinutes(30);
        Date date = Date.from(thirtyMinutesAgo.atZone(ZoneId.systemDefault()).toInstant());
        parkingSession.setStartTime(date);

        ReflectionTestUtils.setField(parkingSessionService, "fractionInMinutes", 15);
        ReflectionTestUtils.setField(parkingSessionService, "pricePerFraction", 30.0);

        when(parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(1L))
                .thenReturn(Optional.of(parkingSession));

        when(parkingSessionRepository.save(parkingSession)).thenReturn(parkingSession);

        parkingSessionService.finishParkingSession("authHeader");

        assertNotNull(parkingSession.getEndTime());

        assertEquals(40.0, currentAccount.getBalance());

    }

    @Test
    public void testGetParkingSessionHistory() {

        NumberPlate numberPlate = new NumberPlate("ABC123");
        user.addNumberPlate(numberPlate);

        List<ParkingSession> parkingSessions = new ArrayList<>();

        ParkingSession p1 = new ParkingSession(numberPlate, currentAccount);
        ParkingSession p2 = new ParkingSession(numberPlate, currentAccount);
        ParkingSession p3 = new ParkingSession(numberPlate, currentAccount);

        p1.setEndTime(new Date());
        p2.setEndTime(new Date());
        p3.setEndTime(new Date());

        p1.setAmount(100.0);
        p2.setAmount(680.0);
        p3.setAmount(2500.0);

        parkingSessions.add(p1);
        parkingSessions.add(p2);
        parkingSessions.add(p3);

        when(parkingSessionRepository.findAllByCurrentAccountIdAndEndTimeNotNull(1L)).thenReturn(parkingSessions);

        ParkingSessionHistory parkingSessionHistory = new ParkingSessionHistory();

        ParkingSessionResponse pr1 = new ParkingSessionResponse(p1.getStarTimeDay(), p1.getStarTimeHour(),
                p1.getEndTimeHour(), p1.getAmount());

        ParkingSessionResponse pr2 = new ParkingSessionResponse(p2.getStarTimeDay(), p2.getStarTimeHour(),
                p2.getEndTimeHour(), p2.getAmount());

        ParkingSessionResponse pr3 = new ParkingSessionResponse(p3.getStarTimeDay(), p3.getStarTimeHour(),
                p3.getEndTimeHour(), p3.getAmount());

        parkingSessionHistory.addParkingSession(pr1);
        parkingSessionHistory.addParkingSession(pr2);
        parkingSessionHistory.addParkingSession(pr3);

        assertEquals(parkingSessionHistory, parkingSessionService.getParkingSessionHistory("authHeader"));

    }
}
