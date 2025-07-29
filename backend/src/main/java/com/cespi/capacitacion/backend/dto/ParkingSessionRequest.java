package com.cespi.capacitacion.backend.dto;

import jakarta.validation.constraints.NotEmpty;

public class ParkingSessionRequest {

    @NotEmpty(message = "El campo 'numberPlate' es requerido")
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
