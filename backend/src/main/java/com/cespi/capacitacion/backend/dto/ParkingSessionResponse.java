package com.cespi.capacitacion.backend.dto;

public class ParkingSessionResponse {

    private String startTime;

    public ParkingSessionResponse(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
