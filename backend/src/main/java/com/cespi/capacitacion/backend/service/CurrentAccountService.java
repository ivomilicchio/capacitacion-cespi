package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.BalanceTopUpHistoryDTO;
import com.cespi.capacitacion.backend.dto.CurrentAccountBalanceDTO;
import com.cespi.capacitacion.backend.entity.CurrentAccount;

public interface CurrentAccountService {

    CurrentAccountBalanceDTO getCurrentAccountBalance();
    CurrentAccountBalanceDTO addBalanceToAccount(CurrentAccountBalanceDTO currentAccountBalanceDTO);
    BalanceTopUpHistoryDTO getBalanceTopUpHistory();
    CurrentAccount save(CurrentAccount currentAccount);

}

