package com.cespi.capacitacion.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ExistPhoneNumberException extends RuntimeException {

    public ExistPhoneNumberException() {
        super("Ya hay un usuario registrado con ese número de teléfono");
    }
}
