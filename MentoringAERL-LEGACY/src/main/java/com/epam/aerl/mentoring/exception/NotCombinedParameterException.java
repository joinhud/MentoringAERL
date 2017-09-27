package com.epam.aerl.mentoring.exception;

public class NotCombinedParameterException extends Exception {
    private String code;

    public NotCombinedParameterException() {
        super();
    }

    public NotCombinedParameterException(String message) {
        super(message);
    }

    public NotCombinedParameterException(String message, String code) {
        super(message);
        this.code = code;
    }

    public NotCombinedParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotCombinedParameterException(Throwable cause) {
        super(cause);
    }

    public NotCombinedParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
