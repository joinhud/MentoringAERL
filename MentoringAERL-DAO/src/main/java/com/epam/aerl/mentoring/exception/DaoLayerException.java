package com.epam.aerl.mentoring.exception;

public class DaoLayerException extends Exception {
  private String code;

  public DaoLayerException() {
    super();
  }

  public DaoLayerException(String message) {
    super(message);
  }

  public DaoLayerException(String message, Throwable cause) {
    super(message, cause);
  }

  public DaoLayerException(String message, Throwable cause, String code) {
    super(message, cause);
    this.code = code;
  }

  public DaoLayerException(Throwable cause) {
    super(cause);
  }

  protected DaoLayerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public String getCode() {
    return code;
  }
}
