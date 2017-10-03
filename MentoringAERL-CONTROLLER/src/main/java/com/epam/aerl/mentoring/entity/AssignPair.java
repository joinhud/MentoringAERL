package com.epam.aerl.mentoring.entity;

import java.io.Serializable;
import java.util.List;

public class AssignPair implements Serializable {
  private University university;
  private List<Student> students;

  public AssignPair() {
  }

  public AssignPair(University university, List<Student> students) {
    this.university = university;
    this.students = students;
  }

  public University getUniversity() {
    return university;
  }

  public void setUniversity(University university) {
    this.university = university;
  }

  public List<Student> getStudents() {
    return students;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AssignPair that = (AssignPair) o;

    if (university != null ? !university.equals(that.university) : that.university != null) return false;
    return students != null ? students.equals(that.students) : that.students == null;
  }

  @Override
  public int hashCode() {
    int result = university != null ? university.hashCode() : 0;
    result = 31 * result + (students != null ? students.hashCode() : 0);
    return result;
  }
}
