package com.epam.aerl.mentoring.entity;

import java.util.List;

public class SetUniversitiesWarning implements Warning {
  private String message;
  private List<Long> universitiesId;

  public SetUniversitiesWarning() {
  }

  public SetUniversitiesWarning(String message, List<Long> universitiesId) {
    this.message = message;
    this.universitiesId = universitiesId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<Long> getUniversitiesId() {
    return universitiesId;
  }

  public void setUniversitiesId(List<Long> universitiesId) {
    this.universitiesId = universitiesId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SetUniversitiesWarning that = (SetUniversitiesWarning) o;

    if (message != null ? !message.equals(that.message) : that.message != null) return false;
    return universitiesId != null ? universitiesId.equals(that.universitiesId) : that.universitiesId == null;
  }

  @Override
  public int hashCode() {
    int result = message != null ? message.hashCode() : 0;
    result = 31 * result + (universitiesId != null ? universitiesId.hashCode() : 0);
    return result;
  }
}
