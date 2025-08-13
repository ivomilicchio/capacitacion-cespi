package com.cespi.capacitacion.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "number_plates")
public class NumberPlate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            length = 7,
            nullable = false,
            unique = true)
    private String number;

    @Column(
            nullable = false
    )
    private boolean deleted;

    public NumberPlate() {

    }

    public NumberPlate(String number) {
        this.number = number;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
