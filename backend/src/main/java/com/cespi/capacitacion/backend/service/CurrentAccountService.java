package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.BalanceTopUpHistory;
import com.cespi.capacitacion.backend.dto.CurrentAccountBalance;

public interface CurrentAccountService {

    CurrentAccountBalance getCurrentAccountBalance();
    CurrentAccountBalance addBalanceToAccount(CurrentAccountBalance currentAccountBalance);
    BalanceTopUpHistory getBalanceTopUpHistory();

}

