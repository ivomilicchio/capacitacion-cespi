package com.cespi.capacitacion.backend.entity;

import com.cespi.capacitacion.backend.repository.ParkingSessionRepository;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.*;
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

    @Column(
            nullable = false
    )
    private Date createdAt;

    @Column(
            nullable = false
    )
    private boolean deleted;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "current_account_id")
    private List<Transaction> transactions;


    public CurrentAccount() {
        this.balance = new BigDecimal(0);
        this.transactions = new ArrayList<>();
        this.createdAt = new Date();
        this.deleted = false;
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

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

//    public boolean addParkingSession(ParkingSession parkingSession) {
//        return parkingSessions.add(parkingSession);
//    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

//    public Set<String> getAllParkingSessionStrings() {
//        return this.parkingSessions.stream().map(p -> p.getStartTime()
//                .toString()).collect(Collectors.toSet());
//    }

//    public Set<String> getAllParkingSessionStrings() {
//        Set<ParkingSession>  parkingSessions = transactions.stream().
//                filter(t -> t.getType() == TransactionType.PARKING).collect(Collectors.toSet());
//    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}