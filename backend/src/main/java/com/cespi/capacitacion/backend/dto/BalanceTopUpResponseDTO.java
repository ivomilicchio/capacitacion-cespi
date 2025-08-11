package com.cespi.capacitacion.backend.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class BalanceTopUpResponseDTO {

    private String day;
    private String hour;
    private BigDecimal amount;

    public BalanceTopUpResponseDTO() {

    }

    public BalanceTopUpResponseDTO(String day, String hour, BigDecimal amount) {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BalanceTopUpResponseDTO)) return false;
        BalanceTopUpResponseDTO that = (BalanceTopUpResponseDTO) o;
        return Objects.equals(day, that.day) &&
                Objects.equals(hour, that.hour) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, hour, amount);
    }
}
