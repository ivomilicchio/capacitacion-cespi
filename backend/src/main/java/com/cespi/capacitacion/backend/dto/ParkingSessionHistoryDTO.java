package com.cespi.capacitacion.backend.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParkingSessionHistoryDTO {


    private List<ParkingSessionResponseDTO> parkingSessions;

    public ParkingSessionHistoryDTO() {
        this.parkingSessions = new ArrayList<>();
    }

    public ParkingSessionHistoryDTO(List<ParkingSessionResponseDTO> parkingSessions) {
        this.parkingSessions = parkingSessions;
    }

    public void addParkingSession(ParkingSessionResponseDTO parkingSession) {
        this.parkingSessions.add(parkingSession);
    }

    public List<ParkingSessionResponseDTO> getParkingSessions() {
        return parkingSessions;
    }

    public void setParkingSessions(List<ParkingSessionResponseDTO> parkingSessions) {
        this.parkingSessions = parkingSessions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkingSessionHistoryDTO)) return false;
        ParkingSessionHistoryDTO that = (ParkingSessionHistoryDTO) o;
        return Objects.equals(parkingSessions, that.parkingSessions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parkingSessions);
    }
}
