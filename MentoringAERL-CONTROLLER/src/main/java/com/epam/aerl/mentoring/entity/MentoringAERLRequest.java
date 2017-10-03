package com.epam.aerl.mentoring.entity;

import java.io.Serializable;

public class MentoringAERLRequest<T> implements Serializable {
  private T data;

  public MentoringAERLRequest() {
  }

  public MentoringAERLRequest(T data) {
    this.data = data;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MentoringAERLRequest<?> that = (MentoringAERLRequest<?>) o;

    return data != null ? data.equals(that.data) : that.data == null;
  }

  @Override
  public int hashCode() {
    return data != null ? data.hashCode() : 0;
  }
}
