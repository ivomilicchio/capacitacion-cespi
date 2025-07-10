package com.cespi.capacitacion.backend.controller;

import com.cespi.capacitacion.backend.dto.NumberPlateCreationDTO;
import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.service.NumberPlateServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/number-plates")
public class NumberPlateController {

    private final NumberPlateServiceImpl numberPlateServiceImpl;

    public NumberPlateController(NumberPlateServiceImpl numberPlateServiceImpl) {
        this.numberPlateServiceImpl = numberPlateServiceImpl;
    }

    @PostMapping()
    public NumberPlate save(@RequestBody NumberPlateCreationDTO numberPlateCreationDTO) {
        return numberPlateServiceImpl.save(numberPlateCreationDTO.getNumber());
    }
}
