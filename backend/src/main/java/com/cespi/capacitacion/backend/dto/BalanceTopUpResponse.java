package com.cespi.capacitacion.backend.dto;

public class BalanceTopUpResponse {

    private String time;
    private Double amount;

    public BalanceTopUpResponse() {

    }

    public BalanceTopUpResponse(String time, Double amount) {
        this.time = time;
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
