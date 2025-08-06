package com.cespi.capacitacion.backend.dto;

import java.util.Objects;

public class ParkingSessionResponse {

    private String startTimeDay;
    private String startTimeHour;
    private String endTimeHour;
    private Double amount;

    public ParkingSessionResponse() {

    }

    public ParkingSessionResponse(String startTimeDay, String startTimeHour, String endTimeHour, Double amount) {
        this.startTimeDay = startTimeDay;
        this.startTimeHour = startTimeHour;
        this.endTimeHour = endTimeHour;
        this.amount = amount;
    }

    public ParkingSessionResponse(String startTimeDay, String startTimeHour) {
        this.startTimeDay = startTimeDay;
        this.startTimeHour = startTimeHour;
    }

    public String getStartTimeDay() {
        return startTimeDay;
    }

    public void setStartTimeDay(String startTimeDay) {
        this.startTimeDay = startTimeDay;
    }

    public String getStartTimeHour() {
        return startTimeHour;
    }

    public void setStartTimeHour(String startTimeHour) {
        this.startTimeHour = startTimeHour;
    }

    public String getEndTimeHour() {
        return endTimeHour;
    }

    public void setEndTimeHour(String endTimeHour) {
        this.endTimeHour = endTimeHour;
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
        if (!(o instanceof ParkingSessionResponse)) return false;
        ParkingSessionResponse that = (ParkingSessionResponse) o;
        return Objects.equals(startTimeDay, that.startTimeDay) &&
                Objects.equals(startTimeHour, that.startTimeHour) &&
                Objects.equals(endTimeHour, that.endTimeHour) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTimeDay, startTimeHour, endTimeHour, amount);
    }
}
