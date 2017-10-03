package com.epam.aerl.mentoring.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MentoringAERLResponse<T> implements Serializable {
  private T data;
  private List<ResponseError> errors = new ArrayList<>();
  private List<Warning> warnings = new ArrayList<>();

  public MentoringAERLResponse() {
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public void addError(ResponseError error) {
    errors.add(error);
  }

  public void addWarning(Warning warning) {
    warnings.add(warning);
  }

  public List<ResponseError> getErrors() {
    return errors;
  }

  public List<Warning> getWarnings() {
    return warnings;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MentoringAERLResponse<?> response = (MentoringAERLResponse<?>) o;

    if (data != null ? !data.equals(response.data) : response.data != null) return false;
    if (errors != null ? !errors.equals(response.errors) : response.errors != null) return false;
    return warnings != null ? warnings.equals(response.warnings) : response.warnings == null;
  }

  @Override
  public int hashCode() {
    int result = data != null ? data.hashCode() : 0;
    result = 31 * result + (errors != null ? errors.hashCode() : 0);
    result = 31 * result + (warnings != null ? warnings.hashCode() : 0);
    return result;
  }
}
