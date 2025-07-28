package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.NumberPlateCreation;
import com.cespi.capacitacion.backend.dto.NumberPlateListResponse;
import com.cespi.capacitacion.backend.entity.NumberPlate;

public interface NumberPlateService {

    NumberPlateListResponse getNumberPlatesOfUser(String authHeader);
    NumberPlateCreation saveNumberPlate(String number, String authHeader);
    NumberPlate  findByNumber(String number);


}
