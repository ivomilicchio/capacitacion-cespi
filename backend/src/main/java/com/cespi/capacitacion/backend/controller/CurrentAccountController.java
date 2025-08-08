package com.cespi.capacitacion.backend.controller;

import com.cespi.capacitacion.backend.dto.BalanceTopUpHistory;
import com.cespi.capacitacion.backend.dto.CurrentAccountBalance;
import com.cespi.capacitacion.backend.service.CurrentAccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/current-accounts/balance")
public class CurrentAccountController {

    private final CurrentAccountService currentAccountService;

    public CurrentAccountController(CurrentAccountService currentAccountService) {
        this.currentAccountService = currentAccountService;
    }

    @GetMapping
    public ResponseEntity<CurrentAccountBalance> getCurrentAccountBalance() {
        return ResponseEntity.ok(currentAccountService.getCurrentAccountBalance());
    }

    @PostMapping
    public ResponseEntity<CurrentAccountBalance> addBalanceToAccount(
            @RequestBody @Valid CurrentAccountBalance currentAccountBalance) {
        return ResponseEntity.ok(currentAccountService.addBalanceToAccount(currentAccountBalance));
    }

    @GetMapping("/history")
    public ResponseEntity<BalanceTopUpHistory> getBalanceTopUpHistory () {
        return ResponseEntity.ok(currentAccountService.getBalanceTopUpHistory());
    }
}
