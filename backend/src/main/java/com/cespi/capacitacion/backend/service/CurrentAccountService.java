package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.CurrentAccountBalanceResponse;
import org.springframework.http.ResponseEntity;

public interface CurrentAccountService {
    CurrentAccountBalanceResponse getCurrentAccountBalance(String authHeader);
}

