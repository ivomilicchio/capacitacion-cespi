package com.cespi.capacitacion.backend.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false
    )
    private Date createdAt;

    @Column(
            nullable = false
    )
    private TransactionType type;

    @Column(
            nullable = false
    )
    private boolean deleted;

    private BigDecimal amount;

    public Transaction() {
        this.createdAt = new Date();
        this.deleted =  false;
    }

    public Transaction(TransactionType type) {
        this();
        this.type = type;
    }

    public Transaction(TransactionType type, BigDecimal amount) {
        this();
        this.type = type;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
