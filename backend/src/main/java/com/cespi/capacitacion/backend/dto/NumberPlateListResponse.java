package com.cespi.capacitacion.backend.dto;

import java.util.HashSet;
import java.util.Set;

public class NumberPlateListResponse {

    Set<String> numberPlates;

    public NumberPlateListResponse() {
       numberPlates = new HashSet<>();
    }

    public NumberPlateListResponse(Set<String> numberPlates) {
        this.numberPlates = numberPlates;
    }

    public Set<String> getNumberPlates() {
        return numberPlates;
    }

    public void setNumberPlates(Set<String> numberPlates) {
        this.numberPlates = numberPlates;
    }
}
