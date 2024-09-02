package com.alabtaal.library.enumeration;

import com.alabtaal.library.exception.BadRequestException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum ActivationStatus {

  PENDING("Pending"),
  ACTIVE("Active"),
  INACTIVE("Inactive"),
  REJECTED("Rejected");

  private final String value;

  ActivationStatus(final String value) {
    this.value = value;
  }

  @JsonCreator
  public static ActivationStatus fromValue(final String value)
      throws BadRequestException {
    return Arrays.stream(ActivationStatus.values())
        .filter(status -> status.getValue().equalsIgnoreCase(value))
        .findFirst()
        .orElseThrow(() -> new BadRequestException(
            "Activation Status with value " + value + " not found"));
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
