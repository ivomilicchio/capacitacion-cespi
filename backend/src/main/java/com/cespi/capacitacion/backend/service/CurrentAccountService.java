package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.BalanceTopUpHistory;
import com.cespi.capacitacion.backend.dto.CurrentAccountBalance;

public interface CurrentAccountService {

    CurrentAccountBalance getCurrentAccountBalance(String authHeader);
    CurrentAccountBalance addBalanceToAccount(String authHeader, CurrentAccountBalance currentAccountBalance);
    BalanceTopUpHistory getBalanceTopUpHistory(String authHeader);

}

