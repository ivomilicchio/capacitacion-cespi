package com.cespi.capacitacion.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadFormatPhoneNumberException extends RuntimeException{

    public BadFormatPhoneNumberException() {
        super("El formato del número de teléfono introducido no es correcto");
    }
}
