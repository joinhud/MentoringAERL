package com.epam.aerl.mentoring.entity;

import com.epam.aerl.mentoring.type.Subject;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "\"STUDENT_MARK\"")
public class StudentMark extends Entity {
  @Id
  @Column(name = "\"STMRK_ID\"", nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STMRK_SEQ_GEN")
  @SequenceGenerator(name = "STMRK_SEQ_GEN", sequenceName = "\"STUDENT_MARK_STMRK_ID_seq\"", allocationSize = 1)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "\"STMRK_STDN_ID\"", nullable = false)
  private Student student;

  @Column(name = "\"STMRK_SUBJECT\"", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Subject subject;

  @Column(name = "\"STMRK_MARK\"", nullable = false)
  private Integer mark;

  public StudentMark() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public Integer getMark() {
    return mark;
  }

  public void setMark(Integer mark) {
    this.mark = mark;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    StudentMark that = (StudentMark) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (student != null ? !student.equals(that.student) : that.student != null) return false;
    if (subject != that.subject) return false;
    return mark != null ? mark.equals(that.mark) : that.mark == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (student != null ? student.hashCode() : 0);
    result = 31 * result + (subject != null ? subject.hashCode() : 0);
    result = 31 * result + (mark != null ? mark.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "StudentMark{" +
        "id=" + id +
        ", student [ID=" + student.getId() + "]" +
        ", subject=" + subject +
        ", mark=" + mark +
        '}';
  }
}
