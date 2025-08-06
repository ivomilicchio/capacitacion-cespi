package com.cespi.capacitacion.backend.dto;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BalanceTopUpResponse)) return false;
        BalanceTopUpResponse that = (BalanceTopUpResponse) o;
        return Objects.equals(day, that.day) &&
                Objects.equals(hour, that.hour) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, hour, amount);
    }
}
