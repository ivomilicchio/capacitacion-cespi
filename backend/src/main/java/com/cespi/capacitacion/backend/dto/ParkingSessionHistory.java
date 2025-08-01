package com.cespi.capacitacion.backend.dto;

import com.cespi.capacitacion.backend.entity.ParkingSession;

import java.util.ArrayList;
import java.util.List;

public class ParkingSessionHistory {


    private List<ParkingSessionResponse> parkingSessions;

    public ParkingSessionHistory() {
        this.parkingSessions = new ArrayList<>();
    }

    public ParkingSessionHistory(List<ParkingSessionResponse> parkingSessions) {
        this.parkingSessions = parkingSessions;
    }

    public void addParkingSession(ParkingSessionResponse parkingSession) {
        this.parkingSessions.add(parkingSession);
    }

    public List<ParkingSessionResponse> getParkingSessions() {
        return parkingSessions;
    }

    public void setParkingSessions(List<ParkingSessionResponse> parkingSessions) {
        this.parkingSessions = parkingSessions;
    }
}
