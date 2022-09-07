package com.example.customerportal.apis;

import com.example.customerportal.exceptions.DomainInvariantException;
import com.example.customerportal.exceptions.RecordNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandlerAdvice {
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?> handleRecordNotFoundException(RecordNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(DomainInvariantException.class)
    public ResponseEntity<?> handleDomainInvariantException(DomainInvariantException exception) {
        return ResponseEntity.badRequest().build();
    }
}
