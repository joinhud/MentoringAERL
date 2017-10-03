package com.epam.aerl.mentoring.entity;

import com.epam.aerl.mentoring.type.UniversityStatus;

import java.time.LocalDate;

public class University extends Entity {
  private Long id;
  private String name;
  private String description;
  private UniversityStatus status;
  private LocalDate foundationDate;

  public University() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public UniversityStatus getStatus() {
    return status;
  }

  public void setStatus(UniversityStatus status) {
    this.status = status;
  }

  public LocalDate getFoundationDate() {
    return foundationDate;
  }

  public void setFoundationDate(LocalDate foundationDate) {
    this.foundationDate = foundationDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    University that = (University) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    if (description != null ? !description.equals(that.description) : that.description != null) return false;
    if (status != that.status) return false;
    return foundationDate != null ? foundationDate.equals(that.foundationDate) : that.foundationDate == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    result = 31 * result + (foundationDate != null ? foundationDate.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "University{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", status=" + status +
        ", foundationDate=" + foundationDate +
        '}';
  }
}
