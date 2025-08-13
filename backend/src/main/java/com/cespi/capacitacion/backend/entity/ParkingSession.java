package com.cespi.capacitacion.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "parking_sessions")
public class ParkingSession extends Transaction {

    @Column(
            nullable = false
    )
    private Date startTime;

    @Column
    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "number_plate_id")
    private NumberPlate numberPlate;

    public ParkingSession() {

    }

    public ParkingSession(NumberPlate numberPlate) {
        super(TransactionType.PARKING_SESSION);
        this.numberPlate = numberPlate;
        this.startTime = new Date();
    }

    public NumberPlate getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(NumberPlate numberPlate) {
        this.numberPlate = numberPlate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getDurationInMinutes() {
        long diffMillis = endTime.getTime() - startTime.getTime();
        return TimeUnit.MILLISECONDS.toMinutes(diffMillis);
    }

    public String getStartTimeDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(this.startTime);
    }

    public String getStartTimeHour() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(this.startTime);
    }

    public String getEndTimeHour() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(this.endTime);
    }
}
