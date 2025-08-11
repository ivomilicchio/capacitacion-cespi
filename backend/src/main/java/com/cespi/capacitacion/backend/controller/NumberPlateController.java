package com.cespi.capacitacion.backend.controller;

import com.cespi.capacitacion.backend.dto.NumberPlateCreationDTO;
import com.cespi.capacitacion.backend.dto.NumberPlateListResponseDTO;
import com.cespi.capacitacion.backend.service.NumberPlateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/number-plates")
public class NumberPlateController {

    private final NumberPlateService numberPlateService;

    public NumberPlateController(NumberPlateService numberPlateService) {
        this.numberPlateService = numberPlateService;
    }

    @GetMapping
    public ResponseEntity<NumberPlateListResponseDTO> getNumberPlatesOfUser() {
        return ResponseEntity.ok(numberPlateService.getNumberPlatesOfUser());
    }

    @PostMapping
    public ResponseEntity<NumberPlateCreationDTO> saveNumberPlate(
            @RequestBody @Valid NumberPlateCreationDTO numberPlateCreationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(numberPlateService.saveNumberPlate(
                numberPlateCreationDTO.getNumber()));
    }

}
