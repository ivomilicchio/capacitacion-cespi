package com.cespi.capacitacion.backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "current_accounts")
public class CurrentAccount {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            nullable = false
    )
    private float balance;
    @OneToMany(mappedBy = "currentAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingSession> parkingSessions;

    public CurrentAccount() {
        this.balance = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}