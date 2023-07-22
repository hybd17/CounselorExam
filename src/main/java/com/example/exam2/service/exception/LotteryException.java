package com.example.exam2.service.exception;

public class LotteryException extends ServiceException{
    public LotteryException() {
        super();
    }

    public LotteryException(String message) {
        super(message);
    }

    public LotteryException(String message, Throwable cause) {
        super(message, cause);
    }

    public LotteryException(Throwable cause) {
        super(cause);
    }

    protected LotteryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
