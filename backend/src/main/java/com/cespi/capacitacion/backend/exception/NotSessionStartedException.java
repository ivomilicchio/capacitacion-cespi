package com.cespi.capacitacion.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class NotSessionStartedException extends RuntimeException {

    public NotSessionStartedException() {
        super("El usuario no posee un servicio de estacionamiento iniciado");
    }
}
