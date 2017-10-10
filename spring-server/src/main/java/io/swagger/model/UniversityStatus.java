package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets UniversityStatus
 */
public enum UniversityStatus {
  
  OPEN("OPEN"),
  
  CLOSED("CLOSED"),
  
  ON_HOLIDAYS("ON_HOLIDAYS"),
  
  PENDING_GOVERNMENT_APPROVAL("PENDING_GOVERNMENT_APPROVAL");

  private String value;

  UniversityStatus(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static UniversityStatus fromValue(String text) {
    for (UniversityStatus b : UniversityStatus.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

