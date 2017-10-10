package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * StudentMarks
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-10T10:52:14.903Z")

public class StudentMarks   {
  @JsonProperty("MATH")
  private Integer MATH = null;

  @JsonProperty("PHYSICAL_EDUCATION")
  private Integer PHYSICAL_EDUCATION = null;

  @JsonProperty("PHILOSOPHY")
  private Integer PHILOSOPHY = null;

  public StudentMarks MATH(Integer MATH) {
    this.MATH = MATH;
    return this;
  }

   /**
   * Get MATH
   * @return MATH
  **/
  @ApiModelProperty(value = "")


  public Integer getMATH() {
    return MATH;
  }

  public void setMATH(Integer MATH) {
    this.MATH = MATH;
  }

  public StudentMarks PHYSICAL_EDUCATION(Integer PHYSICAL_EDUCATION) {
    this.PHYSICAL_EDUCATION = PHYSICAL_EDUCATION;
    return this;
  }

   /**
   * Get PHYSICAL_EDUCATION
   * @return PHYSICAL_EDUCATION
  **/
  @ApiModelProperty(value = "")


  public Integer getPHYSICALEDUCATION() {
    return PHYSICAL_EDUCATION;
  }

  public void setPHYSICALEDUCATION(Integer PHYSICAL_EDUCATION) {
    this.PHYSICAL_EDUCATION = PHYSICAL_EDUCATION;
  }

  public StudentMarks PHILOSOPHY(Integer PHILOSOPHY) {
    this.PHILOSOPHY = PHILOSOPHY;
    return this;
  }

   /**
   * Get PHILOSOPHY
   * @return PHILOSOPHY
  **/
  @ApiModelProperty(value = "")


  public Integer getPHILOSOPHY() {
    return PHILOSOPHY;
  }

  public void setPHILOSOPHY(Integer PHILOSOPHY) {
    this.PHILOSOPHY = PHILOSOPHY;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StudentMarks studentMarks = (StudentMarks) o;
    return Objects.equals(this.MATH, studentMarks.MATH) &&
        Objects.equals(this.PHYSICAL_EDUCATION, studentMarks.PHYSICAL_EDUCATION) &&
        Objects.equals(this.PHILOSOPHY, studentMarks.PHILOSOPHY);
  }

  @Override
  public int hashCode() {
    return Objects.hash(MATH, PHYSICAL_EDUCATION, PHILOSOPHY);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StudentMarks {\n");
    
    sb.append("    MATH: ").append(toIndentedString(MATH)).append("\n");
    sb.append("    PHYSICAL_EDUCATION: ").append(toIndentedString(PHYSICAL_EDUCATION)).append("\n");
    sb.append("    PHILOSOPHY: ").append(toIndentedString(PHILOSOPHY)).append("\n");
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

