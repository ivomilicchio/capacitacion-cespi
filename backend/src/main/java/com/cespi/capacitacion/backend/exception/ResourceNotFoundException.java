package com.cespi.capacitacion.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private String fieldName;

    public ResourceNotFoundException(String fieldName) {
        super(String.format("%s No encontrado", fieldName));
    }
}
