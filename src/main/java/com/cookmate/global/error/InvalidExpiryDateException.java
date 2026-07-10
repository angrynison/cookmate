package com.cookmate.global.error;

public class InvalidExpiryDateException extends RuntimeException {
    public InvalidExpiryDateException(String message) {
        super(message);
    }
}
