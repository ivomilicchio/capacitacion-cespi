package com.cespi.capacitacion.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private BigDecimal balance;
    @OneToMany(mappedBy = "currentAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingSession> parkingSessions;

    @OneToMany(mappedBy = "currentAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BalanceTopUp> balanceTopUps;

    public CurrentAccount() {
        this.balance = new BigDecimal(0);
        this.balanceTopUps = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void addBalanceTopUp(BalanceTopUp balanceTopUp) {
        this.balanceTopUps.add(balanceTopUp);
    }

    public List<ParkingSession> getParkingSessions() {
        return parkingSessions;
    }

    public List<BalanceTopUp> getBalanceTopUps() {
        return balanceTopUps;
    }

    public Set<String> getAllParkingSessionStrings() {
        return this.parkingSessions.stream().map(p -> p.getStartTime()
                .toString()).collect(Collectors.toSet());
    }
}