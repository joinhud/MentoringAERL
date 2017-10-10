package com.epam.aerl.mentoring.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "\"UNIVERSITY\"")
public class UniversityDTO extends BaseDTO {
  @Id
  @Column(name = "\"UNVR_ID\"", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UNVR_SEQ_GEN")
  @SequenceGenerator(name = "UNVR_SEQ_GEN", sequenceName = "\"UNIVERSITY_UNVR_ID_seq\"", allocationSize = 1)
  private Long id;

  @Column(name = "\"UNVR_NAME\"", nullable = false, length = 15)
  private String name;

  @Column(name = "\"UNVR_DESCRIPTION\"", nullable = false, length = 300)
  private String description;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "\"UNVR_STAT_ID\"")
  private UniversityStatusDTO universityStatusDTO;

  @Column(name = "\"UNVR_FOUNDATION_DATE\"", nullable = false)
  private LocalDate foundationDate;

  @Column(name = "\"UNVR_CREATION_IN_DB\"", nullable = false)
  private LocalDateTime creationInDB;

  @Column(name = "\"UNVR_LAST_UPDATE\"", nullable = false)
  private LocalDateTime lastUpdateInDB;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "universityDTO")
  private Set<StudentDTO> studentDTOs;

  public UniversityDTO() {
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

  public UniversityStatusDTO getUniversityStatusDTO() {
    return universityStatusDTO;
  }

  public void setUniversityStatusDTO(UniversityStatusDTO universityStatusDTO) {
    this.universityStatusDTO = universityStatusDTO;
  }

  public LocalDate getFoundationDate() {
    return foundationDate;
  }

  public void setFoundationDate(LocalDate foundationDate) {
    this.foundationDate = foundationDate;
  }

  public LocalDateTime getCreationInDB() {
    return creationInDB;
  }

  public void setCreationInDB(LocalDateTime creationInDB) {
    this.creationInDB = creationInDB;
  }

  public LocalDateTime getLastUpdateInDB() {
    return lastUpdateInDB;
  }

  public void setLastUpdateInDB(LocalDateTime lastUpdateInDB) {
    this.lastUpdateInDB = lastUpdateInDB;
  }

  public Set<StudentDTO> getStudentDTOs() {
    return studentDTOs;
  }

  public void setStudentDTOs(Set<StudentDTO> studentDTOs) {
    this.studentDTOs = studentDTOs;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UniversityDTO that = (UniversityDTO) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    if (description != null ? !description.equals(that.description) : that.description != null) return false;
    if (universityStatusDTO != null ? !universityStatusDTO.equals(that.universityStatusDTO) : that.universityStatusDTO != null) return false;
    if (foundationDate != null ? !foundationDate.equals(that.foundationDate) : that.foundationDate != null) return false;
    if (creationInDB != null ? !creationInDB.equals(that.creationInDB) : that.creationInDB != null) return false;
    if (lastUpdateInDB != null ? !lastUpdateInDB.equals(that.lastUpdateInDB) : that.lastUpdateInDB != null) return false;
    return studentDTOs != null ? studentDTOs.equals(that.studentDTOs) : that.studentDTOs == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (universityStatusDTO != null ? universityStatusDTO.hashCode() : 0);
    result = 31 * result + (foundationDate != null ? foundationDate.hashCode() : 0);
    result = 31 * result + (creationInDB != null ? creationInDB.hashCode() : 0);
    result = 31 * result + (lastUpdateInDB != null ? lastUpdateInDB.hashCode() : 0);
    result = 31 * result + (studentDTOs != null ? studentDTOs.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("UniversityDTO{");
    builder.append("id=").append(id);
    builder.append(", name='").append(name).append('\'');
    builder.append(", description='").append(description).append('\'');
    builder.append(", universityStatusDTO=").append(universityStatusDTO);
    builder.append(", foundationDate=").append(foundationDate);
    builder.append(", creationInDB=").append(creationInDB);
    builder.append(", lastUpdateInDB=").append(lastUpdateInDB);

    if (studentDTOs != null) {
      builder.append(", studentDTOs=").append(studentDTOs);
    }

    builder.append('}');

    return builder.toString();
  }
}
