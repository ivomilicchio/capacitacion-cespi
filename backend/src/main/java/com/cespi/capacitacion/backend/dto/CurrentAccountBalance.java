package com.cespi.capacitacion.backend.dto;

import jakarta.validation.constraints.DecimalMin;

public class CurrentAccountBalance {

    @DecimalMin(value = "100.00", message = "El monto mínimo de carga es de $100")
    private float balance;

    public CurrentAccountBalance() {

    }

    public CurrentAccountBalance(float balance) {
        this.balance = balance;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
