package com.cespi.capacitacion.backend.dto;

public class ParkingSessionResponse {

    private String startTime;
    private String endTime;
    private Double amount;

    public ParkingSessionResponse() {

    }

    public ParkingSessionResponse(String startTime, String endTime, Double amount) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.amount = amount;
    }

    public ParkingSessionResponse(String startTime, Double amount) {
        this.startTime = startTime;
        this.amount = amount;
    }

    public ParkingSessionResponse(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
