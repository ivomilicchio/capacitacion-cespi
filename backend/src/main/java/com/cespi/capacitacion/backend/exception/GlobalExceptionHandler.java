package com.cespi.capacitacion.backend.exception;

import com.cespi.capacitacion.backend.dto.ErrorResponseDTO;
import org.springframework.dao.DataAccessException;
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
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                            WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(exception.getMessage(), webRequest.getDescription(
                false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception, WebRequest webRequest) {
        String errors = exception.getBindingResult().getAllErrors().stream().map( e -> e.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(errors, webRequest.getDescription(
                false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception,WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                "El cuerpo de la solicitud no se pudo leer. Asegúrese de enviar un JSON válido.",
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentialsException(BadCredentialsException exception,
                                                                          WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                "Número de teléfono o contraseña incorrectos",
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadFormatNumberPlateException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadFormatNumberPlateException(BadFormatNumberPlateException exception,
                                                                                WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(exception.getMessage(), webRequest.getDescription(
                false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OutOfServiceException.class)
    public ResponseEntity<ErrorResponseDTO> handleOutOfServiceHourException(OutOfServiceException exception,
                                                                            WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(exception.getMessage(), webRequest.getDescription(
                false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(HasSessionStartedException.class)
    public ResponseEntity<ErrorResponseDTO> handleHasSessionStartedException(HasSessionStartedException exception,
                                                                             WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(exception.getMessage(), webRequest.getDescription(
                false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AlreadyUsedNumberPlateException.class)
    public ResponseEntity<ErrorResponseDTO> handleAlreadyUsedNumberPlateException(AlreadyUsedNumberPlateException exception,
                                                                                  WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(exception.getMessage(), webRequest.getDescription(
                false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponseDTO> handleInsufficientBalanceException(InsufficientBalanceException exception,
                                                                               WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(exception.getMessage(), webRequest.getDescription(
                false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotSessionStartedException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotSessionStartedException(NotSessionStartedException exception,
                                                                             WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(exception.getMessage(), webRequest.getDescription(
                false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadFormatPhoneNumberException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadFormatPhoneNumberException(BadFormatPhoneNumberException exception,
                                                                                WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(exception.getMessage(), webRequest.getDescription(
                false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistPhoneNumberException.class)
    public ResponseEntity<ErrorResponseDTO> handleExistPhoneNumberException(ExistPhoneNumberException exception,
                                                                            WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(exception.getMessage(), webRequest.getDescription(
                false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExistMailException.class)
    public ResponseEntity<ErrorResponseDTO> handleExistMailException(ExistMailException exception,
                                                                     WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(exception.getMessage(), webRequest.getDescription(
                false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponseDTO> handleDatabaseException(DataAccessException exception,
                                                                    WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                "Error interno del servidor. Por favor intentelo más tarde",
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
