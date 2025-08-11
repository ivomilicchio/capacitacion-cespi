package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.auth.AuthService;
import com.cespi.capacitacion.backend.dto.BalanceTopUpHistory;
import com.cespi.capacitacion.backend.dto.BalanceTopUpResponse;
import com.cespi.capacitacion.backend.dto.CurrentAccountBalance;
import com.cespi.capacitacion.backend.entity.BalanceTopUp;
import com.cespi.capacitacion.backend.entity.CurrentAccount;
import com.cespi.capacitacion.backend.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService {

    private final UserService userService;
    private final AuthService authService;

    public CurrentAccountServiceImpl(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    public CurrentAccountBalance getCurrentAccountBalance() {
        User user = authService.getUser();
        return new CurrentAccountBalance(user.getCurrentAccount().getBalance());
    }

    @Transactional
    public CurrentAccountBalance addBalanceToAccount(CurrentAccountBalance currentAccountBalance) {
        User user = authService.getUser();
        CurrentAccount currentAccount = user.getCurrentAccount();
        BigDecimal newBalance = currentAccount.getBalance().add(currentAccountBalance.getBalance());
        currentAccount.setBalance(newBalance);
        BalanceTopUp balanceTopUp = new BalanceTopUp(currentAccountBalance.getBalance(),
                currentAccount);
        currentAccount.addBalanceTopUp(balanceTopUp);
        userService.save(user);
        return new CurrentAccountBalance(newBalance.setScale(2, RoundingMode.HALF_UP));
    }

    public BalanceTopUpHistory getBalanceTopUpHistory() {
        User user = authService.getUser();
        List<BalanceTopUp> balanceTopUps =  user.getCurrentAccount().getBalanceTopUps();
        return this.getHistory(balanceTopUps);
    }

    private BalanceTopUpHistory getHistory(List<BalanceTopUp> balanceTopUps) {
        BalanceTopUpHistory history = new BalanceTopUpHistory();
        for (BalanceTopUp b: balanceTopUps) {
            BalanceTopUpResponse actual = new BalanceTopUpResponse(b.getDay(), b.getHour(),
                    b.getAmount());
            history.addBalanceTopUp(actual);
        }
        return history;
    }

}
