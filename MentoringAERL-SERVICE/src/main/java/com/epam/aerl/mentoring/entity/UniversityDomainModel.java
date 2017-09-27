package com.epam.aerl.mentoring.entity;

import com.epam.aerl.mentoring.type.UniversityStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class UniversityDomainModel extends AbstractDomainModel {
  private Long id;
  private String name;
  private String description;
  private UniversityStatus status;
  private LocalDate foundationDate;
  private LocalDateTime creationInDbDate;
  private LocalDateTime lastUpdateInDbDate;
  private Set<StudentDomainModel> students;

  public UniversityDomainModel() {
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

  public LocalDateTime getCreationInDbDate() {
    return creationInDbDate;
  }

  public void setCreationInDbDate(LocalDateTime creationInDbDate) {
    this.creationInDbDate = creationInDbDate;
  }

  public LocalDateTime getLastUpdateInDbDate() {
    return lastUpdateInDbDate;
  }

  public void setLastUpdateInDbDate(LocalDateTime lastUpdateInDbDate) {
    this.lastUpdateInDbDate = lastUpdateInDbDate;
  }

  public Set<StudentDomainModel> getStudents() {
    return students;
  }

  public void setStudents(Set<StudentDomainModel> students) {
    this.students = students;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UniversityDomainModel that = (UniversityDomainModel) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    if (description != null ? !description.equals(that.description) : that.description != null) return false;
    if (status != that.status) return false;
    if (foundationDate != null ? !foundationDate.equals(that.foundationDate) : that.foundationDate != null) return false;
    if (creationInDbDate != null ? !creationInDbDate.equals(that.creationInDbDate) : that.creationInDbDate != null) return false;
    if (lastUpdateInDbDate != null ? !lastUpdateInDbDate.equals(that.lastUpdateInDbDate) : that.lastUpdateInDbDate != null)
      return false;
    return students != null ? students.equals(that.students) : that.students == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    result = 31 * result + (foundationDate != null ? foundationDate.hashCode() : 0);
    result = 31 * result + (creationInDbDate != null ? creationInDbDate.hashCode() : 0);
    result = 31 * result + (lastUpdateInDbDate != null ? lastUpdateInDbDate.hashCode() : 0);
    result = 31 * result + (students != null ? students.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("UniversityDomainModel{");
    builder.append("id=").append(id);

    if (name != null) {
      builder.append(", name='").append(name).append('\'');
    }

    if (description != null) {
      builder.append(", description='").append(description).append('\'');
    }

    if (status != null) {
      builder.append(", status=").append(status);
    }

    if (foundationDate != null) {
      builder.append(", foundationDate=").append(foundationDate);
    }

    if (creationInDbDate != null) {
      builder.append(", creationInDB=").append(creationInDbDate);
    }

    if (lastUpdateInDbDate != null) {
      builder.append(", lastUpdateInDB=").append(lastUpdateInDbDate);
    }

    if (students != null) {
      builder.append(", studentDTOs=").append(students);
    }

    builder.append('}');

    return builder.toString();
  }
}
