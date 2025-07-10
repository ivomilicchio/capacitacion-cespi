package com.cespi.capacitacion.backend.dto;

public class ParkingSessionRequest {

    private String phoneNumber;
    private String numberPlate;

    public ParkingSessionRequest() {

    }

    public ParkingSessionRequest(String phoneNumber, String numberPlate) {
        this.phoneNumber = phoneNumber;
        this.numberPlate = numberPlate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }
}
