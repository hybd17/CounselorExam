package com.example.exam2.service.exception;

public class StatisticsException extends RuntimeException{
    public StatisticsException() {
        super();
    }

    public StatisticsException(String message) {
        super(message);
    }

    public StatisticsException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatisticsException(Throwable cause) {
        super(cause);
    }

    protected StatisticsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
