package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AssignStudentsResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-10T10:52:14.903Z")

public class AssignStudentsResponse   {
  @JsonProperty("data")
  private List<Long> data = null;

  @JsonProperty("errors")
  private List<Object> errors = null;

  @JsonProperty("warnings")
  private List<Object> warnings = null;

  public AssignStudentsResponse data(List<Long> data) {
    this.data = data;
    return this;
  }

  public AssignStudentsResponse addDataItem(Long dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<Long>();
    }
    this.data.add(dataItem);
    return this;
  }

   /**
   * Get data
   * @return data
  **/
  @ApiModelProperty(value = "")


  public List<Long> getData() {
    return data;
  }

  public void setData(List<Long> data) {
    this.data = data;
  }

  public AssignStudentsResponse errors(List<Object> errors) {
    this.errors = errors;
    return this;
  }

  public AssignStudentsResponse addErrorsItem(Object errorsItem) {
    if (this.errors == null) {
      this.errors = new ArrayList<Object>();
    }
    this.errors.add(errorsItem);
    return this;
  }

   /**
   * Get errors
   * @return errors
  **/
  @ApiModelProperty(value = "")


  public List<Object> getErrors() {
    return errors;
  }

  public void setErrors(List<Object> errors) {
    this.errors = errors;
  }

  public AssignStudentsResponse warnings(List<Object> warnings) {
    this.warnings = warnings;
    return this;
  }

  public AssignStudentsResponse addWarningsItem(Object warningsItem) {
    if (this.warnings == null) {
      this.warnings = new ArrayList<Object>();
    }
    this.warnings.add(warningsItem);
    return this;
  }

   /**
   * Get warnings
   * @return warnings
  **/
  @ApiModelProperty(value = "")


  public List<Object> getWarnings() {
    return warnings;
  }

  public void setWarnings(List<Object> warnings) {
    this.warnings = warnings;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssignStudentsResponse assignStudentsResponse = (AssignStudentsResponse) o;
    return Objects.equals(this.data, assignStudentsResponse.data) &&
        Objects.equals(this.errors, assignStudentsResponse.errors) &&
        Objects.equals(this.warnings, assignStudentsResponse.warnings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, errors, warnings);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssignStudentsResponse {\n");
    
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
    sb.append("    warnings: ").append(toIndentedString(warnings)).append("\n");
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

