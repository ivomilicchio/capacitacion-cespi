package com.cespi.capacitacion.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class OutOfServiceHourException extends RuntimeException {

    public OutOfServiceHourException(String startTime, String endTime) {
        super(String.format("El servicio de estacionamiento medido opera entre las %shs y las %shs",
                startTime, endTime));
    }

}
