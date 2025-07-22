package com.cespi.capacitacion.backend.exception;

import com.cespi.capacitacion.backend.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                          WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), webRequest.getDescription(
                false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception, WebRequest webRequest) {
        String errors = exception.getBindingResult().getAllErrors().stream().map( e -> e.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        ErrorResponse errorResponse = new ErrorResponse(errors, webRequest.getDescription(
                false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception,WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                "El cuerpo de la solicitud no se pudo leer. Asegúrese de enviar un JSON válido.",
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException exception,
                                                                       WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Número de teléfono o contraseña incorrectos",
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadFormatNumberPlateException.class)
    public ResponseEntity<ErrorResponse> handleBadFormatNumberPlateException(BadFormatNumberPlateException exception,
                                                                         WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), webRequest.getDescription(
                false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
