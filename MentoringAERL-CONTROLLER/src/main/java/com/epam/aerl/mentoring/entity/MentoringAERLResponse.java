package com.epam.aerl.mentoring.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MentoringAERLResponse<T> implements Serializable {
  private T data;
  private List<String> errors = new ArrayList<>();
  private List<Warning> warnings = new ArrayList<>();

  public MentoringAERLResponse() {
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public void addErrorMessage(String errorMessage) {
    errors.add(errorMessage);
  }

  public void addWarning(Warning warning) {
    warnings.add(warning);
  }

  public List<String> getErrors() {
    return errors;
  }

  public List<Warning> getWarnings() {
    return warnings;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MentoringAERLResponse<?> that = (MentoringAERLResponse<?>) o;

    if (data != null ? !data.equals(that.data) : that.data != null) return false;
    if (errors != null ? !errors.equals(that.errors) : that.errors != null) return false;
    return warnings != null ? warnings.equals(that.warnings) : that.warnings == null;
  }

  @Override
  public int hashCode() {
    int result = data != null ? data.hashCode() : 0;
    result = 31 * result + (errors != null ? errors.hashCode() : 0);
    result = 31 * result + (warnings != null ? warnings.hashCode() : 0);
    return result;
  }
}
