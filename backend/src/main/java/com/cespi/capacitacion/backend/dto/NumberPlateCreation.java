package com.cespi.capacitacion.backend.dto;

import jakarta.validation.constraints.NotEmpty;

public class NumberPlateCreation {

    @NotEmpty(message = "El campo 'number' es requerido")
    private String number;

    public NumberPlateCreation() {

    }

    public NumberPlateCreation(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
