package com.calero.lili.api.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidIdentificacionException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidIdentificacionException(InvalidIdentificacionException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        apiErrorResponse.setMessage(ex.getMessage());
        apiErrorResponse.setErrors(Collections.singletonList("Identificación inválida"));
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleAlreadyExistsException(AlreadyExistsException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setStatus(HttpStatus.CONFLICT.value());
        apiErrorResponse.setMessage(ex.getMessage());
        apiErrorResponse.setErrors(Collections.singletonList("Recurso ya existente"));
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.CONFLICT);
    }

    /*
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiErrorResponse.setMessage(ex.getMessage());
        apiErrorResponse.setErrors(Collections.singletonList("Error interno del servidor"));
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

     */


}
