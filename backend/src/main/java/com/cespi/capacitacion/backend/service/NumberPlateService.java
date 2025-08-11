package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.NumberPlateCreationDTO;
import com.cespi.capacitacion.backend.dto.NumberPlateListResponseDTO;
import com.cespi.capacitacion.backend.entity.NumberPlate;

public interface NumberPlateService {

    NumberPlateListResponseDTO getNumberPlatesOfUser();
    NumberPlateCreationDTO saveNumberPlate(String number);
    NumberPlate  findByNumber(String number);


}
