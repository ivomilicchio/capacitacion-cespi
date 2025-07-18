package com.cespi.capacitacion.backend.dto;

public class ParkingSessionRequest {

    private String numberPlate;

    public ParkingSessionRequest() {

    }

    public ParkingSessionRequest(String numberPlate) {
        this.numberPlate = numberPlate;
    }


    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }
}
