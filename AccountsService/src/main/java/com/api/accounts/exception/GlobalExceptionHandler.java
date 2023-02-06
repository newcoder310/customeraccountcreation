package com.api.accounts.exception;

import com.api.accounts.model.AccountServiceError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ExternalCallFailedException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected @ResponseBody AccountServiceError handleException(ExternalCallFailedException exception) {
        AccountServiceError error = new AccountServiceError();
        error.setMessage(exception.getMessage());
        return error;
    }

    @ExceptionHandler(value = {CustomerNotPresentException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected @ResponseBody AccountServiceError handleException(CustomerNotPresentException exception) {
        AccountServiceError error = new AccountServiceError();
        error.message(exception.getMessage());
        return error;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AccountServiceError handleException(MethodArgumentTypeMismatchException exception) {

        String err = exception.getParameter().getExecutable().toString();

        AccountServiceError error = new AccountServiceError();
        error.message("Invalid Field Value: " + err);
        return error;
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AccountServiceError handleException(NumberFormatException exception) {

        AccountServiceError error = new AccountServiceError();
        error.message("Request Validation Failed");
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AccountServiceError handleException(MethodArgumentNotValidException exception) {

        String err = exception.getParameter().getExecutable().toString();

        AccountServiceError error = new AccountServiceError();
        error.message("Request Validation Failed");
        return error;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AccountServiceError handleException(ConstraintViolationException exception) {
        AccountServiceError error = new AccountServiceError();
        error.message("Request Validation Failed");
        return error;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody AccountServiceError handleException(Exception exception) {

        AccountServiceError error = new AccountServiceError();
        error.message("Internal Error");
        return error;
    }
}
