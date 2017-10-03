package com.epam.aerl.mentoring.entity;

public class ResponseErrorWithMessage implements ResponseError {
  private String message;

  public ResponseErrorWithMessage() {
  }

  public ResponseErrorWithMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ResponseErrorWithMessage that = (ResponseErrorWithMessage) o;

    return message != null ? message.equals(that.message) : that.message == null;
  }

  @Override
  public int hashCode() {
    return message != null ? message.hashCode() : 0;
  }
}
