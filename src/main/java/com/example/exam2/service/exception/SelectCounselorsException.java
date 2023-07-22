package com.example.exam2.service.exception;

public class SelectCounselorsException extends ServiceException{
    public SelectCounselorsException() {
        super();
    }

    public SelectCounselorsException(String message) {
        super(message);
    }

    public SelectCounselorsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SelectCounselorsException(Throwable cause) {
        super(cause);
    }

    protected SelectCounselorsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
