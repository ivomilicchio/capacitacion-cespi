package com.cespi.capacitacion.backend.controller;

import com.cespi.capacitacion.backend.dto.CurrentAccountBalanceResponse;
import com.cespi.capacitacion.backend.service.CurrentAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/current-accounts")
public class CurrentAccountController {

    private final CurrentAccountService currentAccountService;

    public CurrentAccountController(CurrentAccountService currentAccountService) {
        this.currentAccountService = currentAccountService;
    }

    @GetMapping("/balance")
    public ResponseEntity<CurrentAccountBalanceResponse> getCurrentAccountBalance(
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(currentAccountService.getCurrentAccountBalance(authHeader));
    }
}
