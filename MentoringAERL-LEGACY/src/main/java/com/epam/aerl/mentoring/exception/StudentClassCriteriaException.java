package com.epam.aerl.mentoring.exception;

public class StudentClassCriteriaException extends Exception {
    private String code;

    public StudentClassCriteriaException() {
    }

    public StudentClassCriteriaException(String message) {
        super(message);
    }

    public StudentClassCriteriaException(String message, String code) {
        super(message);
        this.code = code;
    }

    public StudentClassCriteriaException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentClassCriteriaException(Throwable cause) {
        super(cause);
    }

    public StudentClassCriteriaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getCode() {
        return code;
    }
}
