package com.example.customerportal.exceptions;

public class DomainInvariantException extends RuntimeException {
    public DomainInvariantException(String message) {
        super(message);
    }
}
