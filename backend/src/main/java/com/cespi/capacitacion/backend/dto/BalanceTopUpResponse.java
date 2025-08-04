package com.cespi.capacitacion.backend.dto;

public class BalanceTopUpResponse {

    private String day;
    private String hour;
    private Double amount;

    public BalanceTopUpResponse() {

    }

    public BalanceTopUpResponse(String day, String hour, Double amount) {
        this.day = day;
        this.hour = hour;
        this.amount = amount;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
