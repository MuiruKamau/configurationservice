package com.school.configurationservice.exception;

// ResourceNotFoundException.java

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
