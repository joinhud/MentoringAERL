package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.Student;
import io.swagger.model.University;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AssignStudentsRequestData
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-10T10:52:14.903Z")

public class AssignStudentsRequestData   {
  @JsonProperty("university")
  private University university = null;

  @JsonProperty("students")
  private List<Student> students = null;

  public AssignStudentsRequestData university(University university) {
    this.university = university;
    return this;
  }

   /**
   * Get university
   * @return university
  **/
  @ApiModelProperty(value = "")

  @Valid

  public University getUniversity() {
    return university;
  }

  public void setUniversity(University university) {
    this.university = university;
  }

  public AssignStudentsRequestData students(List<Student> students) {
    this.students = students;
    return this;
  }

  public AssignStudentsRequestData addStudentsItem(Student studentsItem) {
    if (this.students == null) {
      this.students = new ArrayList<Student>();
    }
    this.students.add(studentsItem);
    return this;
  }

   /**
   * Get students
   * @return students
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Student> getStudents() {
    return students;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssignStudentsRequestData assignStudentsRequestData = (AssignStudentsRequestData) o;
    return Objects.equals(this.university, assignStudentsRequestData.university) &&
        Objects.equals(this.students, assignStudentsRequestData.students);
  }

  @Override
  public int hashCode() {
    return Objects.hash(university, students);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssignStudentsRequestData {\n");
    
    sb.append("    university: ").append(toIndentedString(university)).append("\n");
    sb.append("    students: ").append(toIndentedString(students)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

