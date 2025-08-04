package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.BalanceTopUpHistory;
import com.cespi.capacitacion.backend.dto.BalanceTopUpResponse;
import com.cespi.capacitacion.backend.dto.CurrentAccountBalance;
import com.cespi.capacitacion.backend.entity.BalanceTopUp;
import com.cespi.capacitacion.backend.entity.CurrentAccount;
import com.cespi.capacitacion.backend.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService {

    private final UserService userService;

    public CurrentAccountServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public CurrentAccountBalance getCurrentAccountBalance(String authHeader) {
        User user = userService.getUserFromAuthHeader(authHeader);
        return new CurrentAccountBalance(user.getCurrentAccount().getBalance());
    }

    @Transactional
    public CurrentAccountBalance addBalanceToAccount(String authHeader, CurrentAccountBalance currentAccountBalance) {
        User user = userService.getUserFromAuthHeader(authHeader);
        CurrentAccount currentAccount = user.getCurrentAccount();
        Double balance = currentAccount.getBalance() + currentAccountBalance.getBalance();
        currentAccount.setBalance(balance);
        BalanceTopUp balanceTopUp = new BalanceTopUp(currentAccountBalance.getBalance(), currentAccount);
        currentAccount.addBalanceTopUp(balanceTopUp);
        userService.save(user);
        return new CurrentAccountBalance(balance);
    }

    @Transactional
    public BalanceTopUpHistory getBalanceTopUpHistory(String authHeader) {
        User user = this.userService.getUserFromAuthHeader(authHeader);
        List<BalanceTopUp> balanceTopUps =  user.getCurrentAccount().getBalanceTopUps();
        return this.getHistory(balanceTopUps);
    }

    private BalanceTopUpHistory getHistory(List<BalanceTopUp> balanceTopUps) {
        BalanceTopUpHistory history = new BalanceTopUpHistory();
        for (BalanceTopUp b: balanceTopUps) {
            BalanceTopUpResponse actual = new BalanceTopUpResponse(b.getDay(), b.getHour(), b.getAmount());
            history.addBalanceTopUp(actual);
        }
        return history;
    }

}
