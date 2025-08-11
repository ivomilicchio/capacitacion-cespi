package com.cespi.capacitacion.backend;

import com.cespi.capacitacion.backend.auth.AuthService;
import com.cespi.capacitacion.backend.dto.BalanceTopUpHistoryDTO;
import com.cespi.capacitacion.backend.dto.BalanceTopUpResponseDTO;
import com.cespi.capacitacion.backend.dto.CurrentAccountBalanceDTO;
import com.cespi.capacitacion.backend.entity.BalanceTopUp;
import com.cespi.capacitacion.backend.entity.CurrentAccount;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.service.CurrentAccountServiceImpl;
import com.cespi.capacitacion.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrentAccountServiceTest {

    @Mock
    private AuthService authService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CurrentAccountServiceImpl currentAccountService;

    private User user;
    private CurrentAccount currentAccount;

    @BeforeEach
    public void setUp() {

        this.user = new User("2211234567", "usuario@gmail.com", "1234");
        this.currentAccount = new CurrentAccount();
        user.setCurrentAccount(currentAccount);

        when(authService.getUser()).thenReturn(user);
    }

    @Test
    public void testGetCurrentAccountBalance() {

        currentAccount.setBalance(BigDecimal.valueOf(1000));
        currentAccountService.addBalanceToAccount(new CurrentAccountBalanceDTO(BigDecimal.valueOf(2000)));

        assertEquals(3000.0, currentAccountService.getCurrentAccountBalance()
                .getBalance());
    }

    @Test
    public void testAddBalanceToAccount() {

        currentAccount.setBalance(BigDecimal.valueOf(1000));

        CurrentAccountBalanceDTO currentAccountBalanceDTO = new CurrentAccountBalanceDTO(BigDecimal.valueOf(500));

        assertEquals(1500, currentAccountService.addBalanceToAccount
                (currentAccountBalanceDTO).getBalance());
    }

    @Test
    public void testGetBalanceTopUpHistory() {

        currentAccount.setBalance(BigDecimal.valueOf(1000));

        BalanceTopUp b1 = new BalanceTopUp(BigDecimal.valueOf(500), currentAccount);
        BalanceTopUp b2 = new BalanceTopUp(BigDecimal.valueOf(600), currentAccount);
        BalanceTopUp b3 = new BalanceTopUp(BigDecimal.valueOf(1400), currentAccount);

        currentAccount.addBalanceTopUp(b1);
        currentAccount.addBalanceTopUp(b2);
        currentAccount.addBalanceTopUp(b3);

        BalanceTopUpHistoryDTO balanceTopUpHistoryDTO = new BalanceTopUpHistoryDTO();

        balanceTopUpHistoryDTO.addBalanceTopUp(new BalanceTopUpResponseDTO(b1.getDay(), b1.getHour(),
                b1.getAmount()));
        balanceTopUpHistoryDTO.addBalanceTopUp(new BalanceTopUpResponseDTO(b2.getDay(), b2.getHour(),
                b2.getAmount()));
        balanceTopUpHistoryDTO.addBalanceTopUp(new BalanceTopUpResponseDTO(b3.getDay(), b3.getHour(),
                b3.getAmount()));

        assertEquals(balanceTopUpHistoryDTO, currentAccountService.getBalanceTopUpHistory());

    }


}
