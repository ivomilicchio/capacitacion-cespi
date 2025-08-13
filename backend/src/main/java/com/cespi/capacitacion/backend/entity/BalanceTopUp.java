package com.cespi.capacitacion.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "balance_top_ups")
public class BalanceTopUp extends Transaction {

    @Column
    private Date time;

    public BalanceTopUp() {

    }

    public BalanceTopUp(BigDecimal amount) {
        super(TransactionType.BALANCE_TOP_UP, amount);
        this.time = new Date();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(this.time);
    }

    public String getHour() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(this.time);
    }
}
