package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.auth.AuthService;
import com.cespi.capacitacion.backend.dto.BalanceTopUpHistoryDTO;
import com.cespi.capacitacion.backend.dto.BalanceTopUpResponseDTO;
import com.cespi.capacitacion.backend.dto.CurrentAccountBalanceDTO;
import com.cespi.capacitacion.backend.entity.BalanceTopUp;
import com.cespi.capacitacion.backend.entity.CurrentAccount;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.repository.CurrentAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService {

    private final UserService userService;
    private final AuthService authService;
    private final CurrentAccountRepository currentAccountRepository;

    public CurrentAccountServiceImpl(UserService userService, AuthService authService,
                                     CurrentAccountRepository currentAccountRepository) {
        this.userService = userService;
        this.authService = authService;
        this.currentAccountRepository = currentAccountRepository;
    }

    public CurrentAccountBalanceDTO getCurrentAccountBalance() {
        User user = authService.getUser();
        return new CurrentAccountBalanceDTO(user.getCurrentAccount().getBalance());
    }

    @Transactional
    public CurrentAccountBalanceDTO addBalanceToAccount(CurrentAccountBalanceDTO currentAccountBalanceDTO) {
        User user = authService.getUser();
        CurrentAccount currentAccount = user.getCurrentAccount();
        BigDecimal newBalance = currentAccount.getBalance().add(currentAccountBalanceDTO.getBalance());
        currentAccount.setBalance(newBalance);
        BalanceTopUp balanceTopUp = new BalanceTopUp(currentAccountBalanceDTO.getBalance());
        currentAccount.addBalanceTopUp(balanceTopUp);
        userService.save(user);
        return new CurrentAccountBalanceDTO(newBalance.setScale(2, RoundingMode.HALF_UP));
    }

    public BalanceTopUpHistoryDTO getBalanceTopUpHistory() {
        User user = authService.getUser();
        List<BalanceTopUp> balanceTopUps =  user.getCurrentAccount().getBalanceTopUps();
        return this.getHistory(balanceTopUps);
    }

    private BalanceTopUpHistoryDTO getHistory(List<BalanceTopUp> balanceTopUps) {
        BalanceTopUpHistoryDTO history = new BalanceTopUpHistoryDTO();
        for (BalanceTopUp b: balanceTopUps) {
            BalanceTopUpResponseDTO actual = new BalanceTopUpResponseDTO(b.getDay(), b.getHour(),
                    b.getAmount());
            history.addBalanceTopUp(actual);
        }
        return history;
    }

    public CurrentAccount save(CurrentAccount currentAccount) {
        return currentAccountRepository.save(currentAccount);
    }

}
