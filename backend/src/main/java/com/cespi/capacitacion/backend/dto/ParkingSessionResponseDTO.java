package com.cespi.capacitacion.backend.dto;

import java.util.Objects;

public class ParkingSessionResponseDTO {

    private String day;
    private String startHour;
    private String endHour;
    private Double amount;


    public ParkingSessionResponseDTO() {

    }

    public ParkingSessionResponseDTO(String day, String startHour, String endHour, Double amount) {
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
        this.amount = amount;
    }

    public ParkingSessionResponseDTO(String day, String startHour) {
        this.day = day;
        this.startHour = startHour;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
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
        if (!(o instanceof ParkingSessionResponseDTO)) return false;
        ParkingSessionResponseDTO that = (ParkingSessionResponseDTO) o;
        return Objects.equals(day, that.day) &&
                Objects.equals(startHour, that.startHour) &&
                Objects.equals(endHour, that.endHour) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, startHour, endHour, amount);
    }
}
