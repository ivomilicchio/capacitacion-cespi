package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.BalanceTopUpHistoryDTO;
import com.cespi.capacitacion.backend.dto.CurrentAccountBalanceDTO;
import com.cespi.capacitacion.backend.entity.CurrentAccount;
import com.cespi.capacitacion.backend.entity.ParkingSession;

import java.util.List;
import java.util.Optional;

public interface CurrentAccountService {

    CurrentAccountBalanceDTO getCurrentAccountBalance();
    CurrentAccountBalanceDTO addBalanceToAccount(CurrentAccountBalanceDTO currentAccountBalanceDTO);
    BalanceTopUpHistoryDTO getBalanceTopUpHistory();
    CurrentAccount save(CurrentAccount currentAccount);
    Optional<ParkingSession> findByCurrentAccountIdAndEndTimeIsNull(Long accountId);
    List<ParkingSession> findAllByCurrentAccountIdAndEndTimeNotNull(Long accountId);

}

