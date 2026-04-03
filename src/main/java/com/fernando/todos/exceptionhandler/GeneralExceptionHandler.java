package com.fernando.todos.exceptionhandler;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.fernando.todos.exception.ExceptionResponses;

@RestControllerAdvice
public class GeneralExceptionHandler {
    
    
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponses> handleException(ResponseStatusException ex) {
        return buildResponseEntity(ex, HttpStatus.valueOf(ex.getStatusCode().value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponses> handleException(Exception ex) {
        return buildResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }
    

    private ResponseEntity<ExceptionResponses> buildResponseEntity(Exception ex, HttpStatus status) {
        ExceptionResponses error = new ExceptionResponses();
        error.setCode(status.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(Instant.now());

        return ResponseEntity.status(status).body(error);
    }
}
