package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.CurrentAccountBalanceResponse;

public interface CurrentAccountService {

    CurrentAccountBalanceResponse getCurrentAccountBalance(String authHeader);
}

