package com.cespi.capacitacion.backend.dto;

public class ParkingSessionResponse {

    private String startTime;
    private String numberPlate;


    public ParkingSessionResponse(String startTime, String numberPlate) {
        this.startTime = startTime;
        this.numberPlate = numberPlate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }
}
