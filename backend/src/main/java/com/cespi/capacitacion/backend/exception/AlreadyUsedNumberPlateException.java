package com.cespi.capacitacion.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AlreadyUsedNumberPlateException extends RuntimeException {

    public AlreadyUsedNumberPlateException() {
        super("La patente con la que se quiere iniciar el servicio ya está siendo utilizada por otro usuario");
    }
}
