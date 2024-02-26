package org.dgp.hw.controllers;

import org.dgp.hw.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandleController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Exception> handleNotFoundException(NotFoundException exc) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exc);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(NotFoundException exc) {
        return ResponseEntity.internalServerError().body(exc.getMessage());
    }
}
