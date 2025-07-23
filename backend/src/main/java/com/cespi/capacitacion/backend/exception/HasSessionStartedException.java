package com.cespi.capacitacion.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class HasSessionStartedException extends RuntimeException {

    public HasSessionStartedException() {
        super("El usuario ya posee un servicio de estacionamiento iniciado");
    }
}
