package com.cespi.capacitacion.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ParkingConfig {

    @Value("${parking.price-per-hour}")
    private Double pricePerHour;

    @Value("${parking.start-time}")
    private String startTime;

    @Value("${parking.end-time}")
    private String endTime;

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
