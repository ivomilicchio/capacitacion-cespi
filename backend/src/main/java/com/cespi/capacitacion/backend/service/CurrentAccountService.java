package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.BalanceTopUpHistoryDTO;
import com.cespi.capacitacion.backend.dto.CurrentAccountBalanceDTO;

public interface CurrentAccountService {

    CurrentAccountBalanceDTO getCurrentAccountBalance();
    CurrentAccountBalanceDTO addBalanceToAccount(CurrentAccountBalanceDTO currentAccountBalanceDTO);
    BalanceTopUpHistoryDTO getBalanceTopUpHistory();

}

