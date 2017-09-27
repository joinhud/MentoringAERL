package com.epam.aerl.mentoring.entity;

import com.epam.aerl.mentoring.type.UniversityStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "\"UNIVERSITY_STATUS\"")
public class UniversityStatusDTO extends BaseDTO {
  @Id
  @Column(name = "\"STAT_ID\"", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STAT_SEQ_GEN")
  @SequenceGenerator(name = "STAT_SEQ_GEN", sequenceName = "\"UNIVERSITY_STATUS_STAT_ID_seq\"", allocationSize = 1)
  private Long id;

  @Column(name = "\"STAT_NAME\"", unique = true, nullable = false)
  @Enumerated(EnumType.STRING)
  private UniversityStatus statusName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UniversityStatus getStatusName() {
    return statusName;
  }

  public void setStatusName(UniversityStatus statusName) {
    this.statusName = statusName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UniversityStatusDTO statusDTO = (UniversityStatusDTO) o;

    if (id != null ? !id.equals(statusDTO.id) : statusDTO.id != null) return false;
    return statusName == statusDTO.statusName;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (statusName != null ? statusName.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "UniversityStatusDTO{" +
        "id=" + id +
        ", statusName=" + statusName +
        '}';
  }
}
