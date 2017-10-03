package com.epam.aerl.mentoring.entity;

import java.util.List;

public class FindStudentsByIdsWarning implements Warning {
  private String message;
  private List<Long> ids;

  public FindStudentsByIdsWarning(String message, List<Long> ids) {
    this.message = message;
    this.ids = ids;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<Long> getIds() {
    return ids;
  }

  public void setIds(List<Long> ids) {
    this.ids = ids;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FindStudentsByIdsWarning that = (FindStudentsByIdsWarning) o;

    if (message != null ? !message.equals(that.message) : that.message != null) return false;
    return ids != null ? ids.equals(that.ids) : that.ids == null;
  }

  @Override
  public int hashCode() {
    int result = message != null ? message.hashCode() : 0;
    result = 31 * result + (ids != null ? ids.hashCode() : 0);
    return result;
  }
}
