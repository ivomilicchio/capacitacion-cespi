package com.cespi.capacitacion.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadFormatNumberPlateException extends RuntimeException {

    public BadFormatNumberPlateException() {
        super("El formato de la patente no es correcto");
    }
}
