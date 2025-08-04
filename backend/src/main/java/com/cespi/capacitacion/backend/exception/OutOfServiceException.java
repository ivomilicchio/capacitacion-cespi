package com.cespi.capacitacion.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class OutOfServiceException extends RuntimeException {

    public OutOfServiceException(String startTime, String endTime) {
        super(String.format("El servicio de estacionamiento medido opera entre las %shs y las %shs",
                startTime, endTime));
    }

    public OutOfServiceException() {
        super("El servicio de estacionamiento medido no opera en el día de hoy");
    }

}
