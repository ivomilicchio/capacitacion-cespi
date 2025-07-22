package com.cespi.capacitacion.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class NumberPlateListResponse {

    List<String> numberPlates;

    public NumberPlateListResponse() {
       numberPlates = new ArrayList<>();
    }

    public NumberPlateListResponse(List<String> numberPlates) {
        this.numberPlates = numberPlates;
    }

    public List<String> getNumberPlates() {
        return numberPlates;
    }

    public void setNumberPlates(List<String> numberPlates) {
        this.numberPlates = numberPlates;
    }
}
