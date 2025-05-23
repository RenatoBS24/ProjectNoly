package com.projectnoly.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>("Ha ocurrido un error interno, por favor intente de nuevo mas tarde", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
