package com.cespi.capacitacion.backend.dto;

import jakarta.validation.constraints.NotEmpty;

public class NumberPlateCreationDTO {

    @NotEmpty(message = "El campo 'number' es requerido")
    private String number;

    public NumberPlateCreationDTO() {

    }

    public NumberPlateCreationDTO(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
