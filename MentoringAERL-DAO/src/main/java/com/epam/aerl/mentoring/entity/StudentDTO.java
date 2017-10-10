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
public class StudentDTO extends BaseDTO {
  @Id
  @Column(name = "\"STDN_ID\"", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STDN_SEQ_GEN")
  @SequenceGenerator(name = "STDN_SEQ_GEN", sequenceName = "\"STUDENT_STDN_ID_seq\"", allocationSize = 1)
	private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "\"STDN_UNVR_ID\"")
	private UniversityDTO universityDTO;

  @Column(name = "\"STDN_NAME\"", nullable = false, length = 20)
	private String name;

  @Column(name = "\"STDN_SURNAME\"", nullable = false, length = 20)
	private String surname;

  @Column(name = "\"STDN_AGE\"", nullable = false)
	private Integer age;

  @Column(name = "\"STDN_COURSE\"", nullable = false)
	private Integer course;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "studentDTO")
	private Set<StudentMarkDTO> marks;

  @Column(name = "\"STDN_CREATION_IN_DB\"", nullable = false)
  private LocalDateTime creationInBD;

  @Column(name = "\"STDN_LAST_UPDATE\"", nullable = false)
  private LocalDateTime lastUpdateInBD;

  public StudentDTO() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UniversityDTO getUniversityDTO() {
    return universityDTO;
  }

  public void setUniversityDTO(UniversityDTO universityDTO) {
    this.universityDTO = universityDTO;
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

  public Set<StudentMarkDTO> getMarks() {
    return marks;
  }

  public void setMarks(Set<StudentMarkDTO> marks) {
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

    StudentDTO studentDTO = (StudentDTO) o;

    if (id != null ? !id.equals(studentDTO.id) : studentDTO.id != null) return false;
    if (universityDTO != null ? !universityDTO.equals(studentDTO.universityDTO) : studentDTO.universityDTO != null) return false;
    if (name != null ? !name.equals(studentDTO.name) : studentDTO.name != null) return false;
    if (surname != null ? !surname.equals(studentDTO.surname) : studentDTO.surname != null) return false;
    if (age != null ? !age.equals(studentDTO.age) : studentDTO.age != null) return false;
    if (course != null ? !course.equals(studentDTO.course) : studentDTO.course != null) return false;
    if (marks != null ? !marks.equals(studentDTO.marks) : studentDTO.marks != null) return false;
    if (creationInBD != null ? !creationInBD.equals(studentDTO.creationInBD) : studentDTO.creationInBD != null) return false;
    return lastUpdateInBD != null ? lastUpdateInBD.equals(studentDTO.lastUpdateInBD) : studentDTO.lastUpdateInBD == null;
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
    String result = "StudentDTO{";
    StringBuilder builder = new StringBuilder(result);
    builder.append("id=").append(id);

    if (universityDTO != null) {
      builder.append(", universityDTO [ID=").append(universityDTO.getId());
      builder.append(", name='").append(universityDTO.getName()).append("'");

      if (universityDTO.getUniversityStatusDTO() != null) {
        builder.append(", status='").append(universityDTO.getUniversityStatusDTO().getStatusName().toString() ).append("']");
      } else {
        builder.append("]");
      }
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
