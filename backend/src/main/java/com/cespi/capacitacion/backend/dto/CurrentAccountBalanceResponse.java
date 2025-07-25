package com.cespi.capacitacion.backend.dto;

public class CurrentAccountBalanceResponse {

    private float balance;

    public CurrentAccountBalanceResponse() {

    }

    public CurrentAccountBalanceResponse(float balance) {
        this.balance = balance;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
