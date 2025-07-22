package com.cespi.capacitacion.backend.controller;

import com.cespi.capacitacion.backend.dto.NumberPlateCreation;
import com.cespi.capacitacion.backend.dto.NumberPlateListResponse;
import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.service.NumberPlateServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/number-plates")
public class NumberPlateController {

    private final NumberPlateServiceImpl numberPlateServiceImpl;

    public NumberPlateController(NumberPlateServiceImpl numberPlateServiceImpl) {
        this.numberPlateServiceImpl = numberPlateServiceImpl;
    }

    @GetMapping
    public ResponseEntity<NumberPlateListResponse> getNumberPlatesOfUser(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(numberPlateServiceImpl.getNumberPlatesOfUser(token));
    }

    @PostMapping
    public ResponseEntity<NumberPlateCreation> saveNumberPlate(
            @RequestBody @Valid NumberPlateCreation numberPlateCreation,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.CREATED).body(numberPlateServiceImpl.saveNumberPlate(
                numberPlateCreation.getNumber(), token));
    }

}
