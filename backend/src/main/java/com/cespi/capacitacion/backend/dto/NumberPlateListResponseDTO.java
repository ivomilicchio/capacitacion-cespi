package com.cespi.capacitacion.backend.dto;

import java.util.HashSet;
import java.util.Set;

public class NumberPlateListResponseDTO {

    Set<String> numberPlates;

    public NumberPlateListResponseDTO() {
       numberPlates = new HashSet<>();
    }

    public NumberPlateListResponseDTO(Set<String> numberPlates) {
        this.numberPlates = numberPlates;
    }

    public Set<String> getNumberPlates() {
        return numberPlates;
    }

    public void setNumberPlates(Set<String> numberPlates) {
        this.numberPlates = numberPlates;
    }
}
