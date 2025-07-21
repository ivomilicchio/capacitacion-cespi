package com.cespi.capacitacion.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "parking_sessions")
public class ParkingSession {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            nullable = false
    )
    private Date startTime;
    @Column
    private Date endTime;
    @ManyToOne
    @JoinColumn(name = "number_plate_id")
    private NumberPlate numberPlate;
    @ManyToOne
    @JoinColumn(name = "current_account_id")
    private CurrentAccount currentAccount;


    public ParkingSession() {

    }

    public ParkingSession(NumberPlate numberPlate, CurrentAccount currentAccount) {
        this.numberPlate = numberPlate;
        this.currentAccount = currentAccount;
        this.startTime = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

//    public long getDuration() {
//        return Duration.between(startTime, endTime).toMinutes();
//
//    }
}
