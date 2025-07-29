package com.cespi.capacitacion.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ExistMailException extends RuntimeException {

    public ExistMailException() {
        super("Ya hay un usuario registrado con ese mail");
    }
}
