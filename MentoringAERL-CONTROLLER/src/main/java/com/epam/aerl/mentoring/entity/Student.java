package com.epam.aerl.mentoring.entity;

import com.epam.aerl.mentoring.type.Subject;

import java.util.Map;

public class Student extends Entity {
  private Long id;
  private String name;
  private String surname;
  private Integer age;
  private Integer course;
  private Map<Subject, Integer> marks;

  public Student() {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Student student = (Student) o;

    if (id != null ? !id.equals(student.id) : student.id != null) return false;
    if (name != null ? !name.equals(student.name) : student.name != null) return false;
    if (surname != null ? !surname.equals(student.surname) : student.surname != null) return false;
    if (age != null ? !age.equals(student.age) : student.age != null) return false;
    if (course != null ? !course.equals(student.course) : student.course != null) return false;
    return marks != null ? marks.equals(student.marks) : student.marks == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (surname != null ? surname.hashCode() : 0);
    result = 31 * result + (age != null ? age.hashCode() : 0);
    result = 31 * result + (course != null ? course.hashCode() : 0);
    result = 31 * result + (marks != null ? marks.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Student{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", surname='" + surname + '\'' +
        ", age=" + age +
        ", course=" + course +
        ", marks=" + marks +
        '}';
  }
}
