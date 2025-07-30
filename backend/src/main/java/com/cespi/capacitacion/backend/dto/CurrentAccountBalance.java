package com.cespi.capacitacion.backend.dto;

import jakarta.validation.constraints.DecimalMin;

public class CurrentAccountBalance {

    @DecimalMin(value = "100.00", message = "El monto mínimo de carga es de $100")
    private Double balance;

    public CurrentAccountBalance() {

    }

    public CurrentAccountBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
