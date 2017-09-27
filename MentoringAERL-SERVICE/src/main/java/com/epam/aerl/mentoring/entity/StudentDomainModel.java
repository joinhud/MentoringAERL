package com.epam.aerl.mentoring.entity;

import com.epam.aerl.mentoring.type.Subject;

import java.time.LocalDateTime;
import java.util.Map;

public class StudentDomainModel extends AbstractDomainModel {
  private Long id;
  private String name;
  private String surname;
  private Integer age;
  private Integer course;
  private UniversityDomainModel university;
  private Map<Subject, Integer> marks;
  private LocalDateTime creationInDbDate;
  private LocalDateTime lastUpdateInDbDate;

  public StudentDomainModel() {
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

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Integer getCourse() {
    return course;
  }

  public void setCourse(Integer course) {
    this.course = course;
  }

  public Map<Subject, Integer> getMarks() {
    return marks;
  }

  public void setMarks(Map<Subject, Integer> marks) {
    this.marks = marks;
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

  public UniversityDomainModel getUniversity() {
    return university;
  }

  public void setUniversity(UniversityDomainModel university) {
    this.university = university;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    StudentDomainModel that = (StudentDomainModel) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
    if (age != null ? !age.equals(that.age) : that.age != null) return false;
    if (course != null ? !course.equals(that.course) : that.course != null) return false;
    if (marks != null ? !marks.equals(that.marks) : that.marks != null) return false;
    if (creationInDbDate != null ? !creationInDbDate.equals(that.creationInDbDate) : that.creationInDbDate != null) return false;
    return lastUpdateInDbDate != null ? lastUpdateInDbDate.equals(that.lastUpdateInDbDate) : that.lastUpdateInDbDate == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (surname != null ? surname.hashCode() : 0);
    result = 31 * result + (age != null ? age.hashCode() : 0);
    result = 31 * result + (course != null ? course.hashCode() : 0);
    result = 31 * result + (marks != null ? marks.hashCode() : 0);
    result = 31 * result + (creationInDbDate != null ? creationInDbDate.hashCode() : 0);
    result = 31 * result + (lastUpdateInDbDate != null ? lastUpdateInDbDate.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    String result = "StudentDomainModel{";
    StringBuilder builder = new StringBuilder(result);
    builder.append("id=").append(id);

    if (name != null) {
      builder.append(", name='").append(name).append("'");
    }

    if (surname != null) {
      builder.append(", surname='").append(surname).append("'");
    }

    if (age != null) {
      builder.append(", age=").append(age);
    }

    if (course != null) {
      builder.append(", course=").append(course);
    }

    if (university != null) {
      builder.append(", universityDomainModel [ID=").append(university.getId());
      builder.append(", name='").append(university.getName()).append("'");

      if (university.getStatus() != null) {
        builder.append(", status='").append(university.getStatus().toString()).append("']");
      } else {
        builder.append(']');
      }
    }

    if (marks != null) {
      builder.append(", marks=").append(marks);
    }

    if (creationInDbDate != null) {
      builder.append(", creationInBD=").append(creationInDbDate);
    }

    if (lastUpdateInDbDate != null) {
      builder.append(", lastUpdateInBD=").append(lastUpdateInDbDate).append('}');
    }

    result = builder.toString();

    return result;
  }
}
