package com.cespi.capacitacion.backend.dto;

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
}
