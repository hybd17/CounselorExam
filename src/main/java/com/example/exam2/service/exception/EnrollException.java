package com.example.exam2.service.exception;

public class EnrollException extends ServiceException{
    public EnrollException() {
        super();
    }

    public EnrollException(String message) {
        super(message);
    }

    public EnrollException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnrollException(Throwable cause) {
        super(cause);
    }

    protected EnrollException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
