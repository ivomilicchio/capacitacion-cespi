package com.cespi.capacitacion.backend.controller;

import com.cespi.capacitacion.backend.dto.CurrentAccountBalance;
import com.cespi.capacitacion.backend.service.CurrentAccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/current-accounts")
public class CurrentAccountController {

    private final CurrentAccountService currentAccountService;

    public CurrentAccountController(CurrentAccountService currentAccountService) {
        this.currentAccountService = currentAccountService;
    }

    @GetMapping("/balance")
    public ResponseEntity<CurrentAccountBalance> getCurrentAccountBalance(
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(currentAccountService.getCurrentAccountBalance(authHeader));
    }

    @PostMapping("/balance")
    public ResponseEntity<CurrentAccountBalance> addBalanceToAccount(
            @RequestBody @Valid CurrentAccountBalance currentAccountBalance,
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(currentAccountService.addBalanceToAccount(authHeader, currentAccountBalance));
    }
}
