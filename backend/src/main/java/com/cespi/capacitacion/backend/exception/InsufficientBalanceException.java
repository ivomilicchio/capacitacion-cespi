package com.cespi.capacitacion.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException() {
        super("El usuario no posee saldo suficiente para solicitar el servicio");
    }
}
