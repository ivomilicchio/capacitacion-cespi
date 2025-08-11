package com.cespi.capacitacion.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;

public class CurrentAccountBalanceDTO {

    @DecimalMin(value = "100.00", message = "El monto mínimo de carga es de $100")
    @Digits(integer = 36, fraction = 2, message = "El monto puede tener como máximo 2 decimales")
    private BigDecimal balance;

    public CurrentAccountBalanceDTO() {

    }

    public CurrentAccountBalanceDTO(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
