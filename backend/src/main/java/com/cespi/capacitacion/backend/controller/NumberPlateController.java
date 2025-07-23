package com.cespi.capacitacion.backend.controller;

import com.cespi.capacitacion.backend.dto.NumberPlateCreation;
import com.cespi.capacitacion.backend.dto.NumberPlateListResponse;
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
    public ResponseEntity<NumberPlateListResponse> getNumberPlatesOfUser(
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(numberPlateService.getNumberPlatesOfUser(authHeader));
    }

    @PostMapping
    public ResponseEntity<NumberPlateCreation> saveNumberPlate(
            @RequestBody @Valid NumberPlateCreation numberPlateCreation,
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.status(HttpStatus.CREATED).body(numberPlateService.saveNumberPlate(
                numberPlateCreation.getNumber(), authHeader));
    }

}
