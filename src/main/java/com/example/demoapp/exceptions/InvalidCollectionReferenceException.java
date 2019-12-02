package com.example.demoapp.exceptions;

/**
 *
 */
public class InvalidCollectionReferenceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidCollectionReferenceException(String errorMessage) {
        super(errorMessage);
    }
}
