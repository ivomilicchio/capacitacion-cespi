package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.CurrentAccountBalance;
import com.cespi.capacitacion.backend.entity.BalanceTopUp;
import com.cespi.capacitacion.backend.entity.CurrentAccount;
import com.cespi.capacitacion.backend.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

}
