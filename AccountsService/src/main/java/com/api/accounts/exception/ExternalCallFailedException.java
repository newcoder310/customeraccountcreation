package com.api.accounts.exception;

public class ExternalCallFailedException extends RuntimeException {
    public ExternalCallFailedException(String message) {
        super(message);
    }
}
