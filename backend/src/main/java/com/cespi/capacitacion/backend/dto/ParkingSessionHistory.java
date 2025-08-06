package com.cespi.capacitacion.backend.dto;

import com.cespi.capacitacion.backend.entity.ParkingSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkingSessionHistory)) return false;
        ParkingSessionHistory that = (ParkingSessionHistory) o;
        return Objects.equals(parkingSessions, that.parkingSessions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parkingSessions);
    }
}
