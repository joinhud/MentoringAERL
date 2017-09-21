package com.epam.aerl.mentoring.entity;


import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "\"STUDENT\"")
public class Student extends Entity {
  @Id
  @Column(name = "\"STDN_ID\"", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STDN_SEQ_GEN")
  @SequenceGenerator(name = "STDN_SEQ_GEN", sequenceName = "\"STUDENT_STDN_ID_seq\"", allocationSize = 1)
	private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "\"STDN_UNVR_ID\"")
	private University university;

  @Column(name = "\"STDN_NAME\"", nullable = false, length = 20)
	private String name;

  @Column(name = "\"STDN_SURNAME\"", nullable = false, length = 20)
	private String surname;

  @Column(name = "\"STDN_AGE\"", nullable = false)
	private Integer age;

  @Column(name = "\"STDN_COURSE\"", nullable = false)
	private Integer course;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
	private Set<StudentMark> marks;

  @Column(name = "\"STDN_CREATION_IN_DB\"", nullable = false)
  private LocalDateTime creationInBD;

  @Column(name = "\"STDN_LAST_UPDATE\"", nullable = false)
  private LocalDateTime lastUpdateInBD;

  public Student() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public University getUniversity() {
    return university;
  }

  public void setUniversity(University university) {
    this.university = university;
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

  public Set<StudentMark> getMarks() {
    return marks;
  }

  public void setMarks(Set<StudentMark> marks) {
    this.marks = marks;
  }

  public LocalDateTime getCreationInBD() {
    return creationInBD;
  }

  public void setCreationInBD(LocalDateTime creationInBD) {
    this.creationInBD = creationInBD;
  }

  public LocalDateTime getLastUpdateInBD() {
    return lastUpdateInBD;
  }

  public void setLastUpdateInBD(LocalDateTime lastUpdateInBD) {
    this.lastUpdateInBD = lastUpdateInBD;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Student student = (Student) o;

    if (id != null ? !id.equals(student.id) : student.id != null) return false;
    if (university != null ? !university.equals(student.university) : student.university != null) return false;
    if (name != null ? !name.equals(student.name) : student.name != null) return false;
    if (surname != null ? !surname.equals(student.surname) : student.surname != null) return false;
    if (age != null ? !age.equals(student.age) : student.age != null) return false;
    if (course != null ? !course.equals(student.course) : student.course != null) return false;
    if (marks != null ? !marks.equals(student.marks) : student.marks != null) return false;
    if (creationInBD != null ? !creationInBD.equals(student.creationInBD) : student.creationInBD != null) return false;
    return lastUpdateInBD != null ? lastUpdateInBD.equals(student.lastUpdateInBD) : student.lastUpdateInBD == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (surname != null ? surname.hashCode() : 0);
    result = 31 * result + (age != null ? age.hashCode() : 0);
    result = 31 * result + (course != null ? course.hashCode() : 0);
    result = 31 * result + (creationInBD != null ? creationInBD.hashCode() : 0);
    result = 31 * result + (lastUpdateInBD != null ? lastUpdateInBD.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    String result = "Student{";
    StringBuilder builder = new StringBuilder(result);
    builder.append("id=").append(id);

    if (university != null) {
      builder.append(", university [ID=").append(university.getId());
      builder.append(", name='").append(university.getName()).append("']");
    }

    builder.append(", name='").append(name).append("'");
    builder.append(", surname='").append(surname).append("'");
    builder.append(", age=").append(age);
    builder.append(", course=").append(course);

    if (marks != null) {
      builder.append(", marks=").append(marks);
    }

    builder.append(", creationInBD=").append(creationInBD);
    builder.append(", lastUpdateInBD=").append(lastUpdateInBD).append('}');

    result = builder.toString();

    return result;
  }
}
