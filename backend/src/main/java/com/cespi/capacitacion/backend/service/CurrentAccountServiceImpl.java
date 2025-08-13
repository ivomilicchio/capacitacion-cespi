package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.auth.AuthService;
import com.cespi.capacitacion.backend.dto.BalanceTopUpHistoryDTO;
import com.cespi.capacitacion.backend.dto.BalanceTopUpResponseDTO;
import com.cespi.capacitacion.backend.dto.CurrentAccountBalanceDTO;
import com.cespi.capacitacion.backend.entity.*;
import com.cespi.capacitacion.backend.repository.CurrentAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService {

    private final AuthService authService;
    private final CurrentAccountRepository currentAccountRepository;

    public CurrentAccountServiceImpl(AuthService authService, CurrentAccountRepository currentAccountRepository) {
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
        currentAccount.addTransaction(balanceTopUp);
        this.save(currentAccount);
        return new CurrentAccountBalanceDTO(newBalance.setScale(2, RoundingMode.HALF_UP));
    }

    public BalanceTopUpHistoryDTO getBalanceTopUpHistory() {
        User user = authService.getUser();
        List<BalanceTopUp> balanceTopUps = currentAccountRepository.findAllByCurrentAccountIdAndType(
                user.getCurrentAccount().getId(), TransactionType.BALANCE_TOP_UP);
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

    public Optional<ParkingSession> findByCurrentAccountIdAndEndTimeIsNull(Long accountId) {
        return currentAccountRepository.findByCurrentAccountIdAndEndTimeIsNull(accountId);
    }

    public List<ParkingSession> findAllByCurrentAccountIdAndEndTimeNotNull(Long accountId) {
        return currentAccountRepository.findAllByCurrentAccountIdAndEndTimeNotNull(accountId);
    }

}
