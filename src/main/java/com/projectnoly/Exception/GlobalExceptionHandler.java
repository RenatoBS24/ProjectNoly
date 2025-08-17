package com.projectnoly.Exception;

import com.projectnoly.DTO.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException ex){
        log.error(ex.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                ex.getMessage()
        );
        return new ResponseEntity<>(exceptionResponse,HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> handlerDuplicateResourceException(DuplicateResourceException ex){
        log.error(ex.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now(),
                ex.getMessage()
        );
        return new ResponseEntity<>(exceptionResponse,HttpStatus.CONFLICT);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>("Ha ocurrido un error interno, por favor intente de nuevo mas tarde", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
