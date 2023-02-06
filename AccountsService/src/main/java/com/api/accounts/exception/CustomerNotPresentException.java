package com.api.accounts.exception;

public class CustomerNotPresentException extends RuntimeException {

    public CustomerNotPresentException(String message) {
        super(message);
    }
}
