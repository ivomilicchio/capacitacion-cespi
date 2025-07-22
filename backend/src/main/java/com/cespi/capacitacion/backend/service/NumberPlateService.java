package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.NumberPlateCreation;
import com.cespi.capacitacion.backend.dto.NumberPlateListResponse;

public interface NumberPlateService {

    NumberPlateListResponse getNumberPlatesOfUser(String token);
    NumberPlateCreation saveNumberPlate(String number, String token);


}
