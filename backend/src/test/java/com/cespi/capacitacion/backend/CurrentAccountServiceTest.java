package com.cespi.capacitacion.backend;

import com.cespi.capacitacion.backend.dto.BalanceTopUpHistory;
import com.cespi.capacitacion.backend.dto.BalanceTopUpResponse;
import com.cespi.capacitacion.backend.dto.CurrentAccountBalance;
import com.cespi.capacitacion.backend.entity.BalanceTopUp;
import com.cespi.capacitacion.backend.entity.CurrentAccount;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.service.CurrentAccountServiceImpl;
import com.cespi.capacitacion.backend.service.UserService;
import net.bytebuddy.asm.MemberSubstitution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrentAccountServiceTest {

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

        when(userService.getUserFromAuthHeader("authHeader")).thenReturn(user);
    }

    @Test
    public void testGetCurrentAccountBalance() {

        currentAccount.setBalance(1000.0);
        currentAccountService.addBalanceToAccount("authHeader", new CurrentAccountBalance(2000.0));

        assertEquals(3000.0, currentAccountService.getCurrentAccountBalance("authHeader")
                .getBalance());
    }

    @Test
    public void testAddBalanceToAccount() {

        currentAccount.setBalance(1000.0);

        when(userService.save(user)).thenReturn(user);

        CurrentAccountBalance currentAccountBalance = new CurrentAccountBalance(500.0);

        assertEquals(1500, currentAccountService.addBalanceToAccount
                ("authHeader", currentAccountBalance).getBalance());
    }

    @Test
    public void testGetBalanceTopUpHistory() {

        currentAccount.setBalance(1000.0);

        BalanceTopUp b1 = new BalanceTopUp(500.0, currentAccount);
        BalanceTopUp b2 = new BalanceTopUp(600.0, currentAccount);
        BalanceTopUp b3 = new BalanceTopUp(1400.0, currentAccount);

        currentAccount.addBalanceTopUp(b1);
        currentAccount.addBalanceTopUp(b2);
        currentAccount.addBalanceTopUp(b3);

        BalanceTopUpHistory balanceTopUpHistory = new BalanceTopUpHistory();

        balanceTopUpHistory.addBalanceTopUp(new BalanceTopUpResponse(b1.getDay(), b1.getHour(), b1.getAmount()));
        balanceTopUpHistory.addBalanceTopUp(new BalanceTopUpResponse(b2.getDay(), b2.getHour(), b2.getAmount()));
        balanceTopUpHistory.addBalanceTopUp(new BalanceTopUpResponse(b3.getDay(), b3.getHour(), b3.getAmount()));

        assertEquals(balanceTopUpHistory, currentAccountService.getBalanceTopUpHistory("authHeader"));

    }


}
