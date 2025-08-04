package com.cespi.capacitacion.backend.entity;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "balance_top_ups")
public class BalanceTopUp {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column
    private Date time;
    @Column(
            nullable = false
    )
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "current_account_id")
    private CurrentAccount currentAccount;

    public BalanceTopUp() {

    }

    public BalanceTopUp(Double amount, CurrentAccount currentAccount) {
        this.time = new Date();
        this.amount = amount;
        this.currentAccount = currentAccount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
