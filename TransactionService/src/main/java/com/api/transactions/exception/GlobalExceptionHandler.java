package com.api.transactions.exception;

import com.api.transactions.model.TransactionServiceError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;


@ControllerAdvice
public class GlobalExceptionHandler{

    public static final String THE_SYSTEM_HAD_AN_INTERNAL_ERROR = "The system had an internal error.";

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody TransactionServiceError handleException(ConstraintViolationException exception) {
        TransactionServiceError transactionServiceError = new TransactionServiceError();
        transactionServiceError.message(THE_SYSTEM_HAD_AN_INTERNAL_ERROR);
        return transactionServiceError;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody TransactionServiceError handleException(MethodArgumentTypeMismatchException exception) {

        String err = exception.getParameter().getExecutable().toString();

        TransactionServiceError error = new TransactionServiceError();
        error.message("Invalid Field Value: " + err);
        return error;
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody TransactionServiceError handleException(NumberFormatException exception) {

        TransactionServiceError error = new TransactionServiceError();
        error.message("Request Validation Failed");
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody TransactionServiceError handleException(MethodArgumentNotValidException exception) {

        String err = exception.getParameter().getExecutable().toString();

        TransactionServiceError error = new TransactionServiceError();
        error.message("Request Validation Failed");
        return error;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody TransactionServiceError handleException(Exception exception) {

        TransactionServiceError error = new TransactionServiceError();
        error.message("Internal Error");
        return error;
    }

}
