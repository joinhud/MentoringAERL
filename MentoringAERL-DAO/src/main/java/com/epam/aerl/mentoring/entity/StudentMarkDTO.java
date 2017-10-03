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
public class StudentMarkDTO extends BaseDTO {
  @Id
  @Column(name = "\"STMRK_ID\"", nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STMRK_SEQ_GEN")
  @SequenceGenerator(name = "STMRK_SEQ_GEN", sequenceName = "\"STUDENT_MARK_STMRK_ID_seq\"", allocationSize = 1)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "\"STMRK_STDN_ID\"", nullable = false)
  private StudentDTO studentDTO;

  @Column(name = "\"STMRK_SUBJECT\"", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Subject subject;

  @Column(name = "\"STMRK_MARK\"", nullable = false)
  private Integer mark;

  public StudentMarkDTO() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public StudentDTO getStudentDTO() {
    return studentDTO;
  }

  public void setStudentDTO(StudentDTO studentDTO) {
    this.studentDTO = studentDTO;
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

    StudentMarkDTO that = (StudentMarkDTO) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (studentDTO != null ? !studentDTO.equals(that.studentDTO) : that.studentDTO != null) return false;
    if (subject != that.subject) return false;
    return mark != null ? mark.equals(that.mark) : that.mark == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (studentDTO != null ? studentDTO.hashCode() : 0);
    result = 31 * result + (subject != null ? subject.hashCode() : 0);
    result = 31 * result + (mark != null ? mark.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    String result = "StudentMarkDTO{";
    StringBuilder builder = new StringBuilder(result);
    builder.append("id=").append(id);

    if (studentDTO != null) {
      builder.append(", studentDTO [ID=").append(studentDTO.getId()).append("]");
    }

    builder.append(", subject=").append(subject);
    builder.append(", mark=").append(mark);
    builder.append("}");

    result = builder.toString();

    return result;
  }
}
