package com.alabtaal.library.enumeration;

import com.alabtaal.library.exception.BadRequestException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum TransType {
  ISSUE("issue"),
  RECEIPT("receipt");

  private final String value;

  TransType(final String value) {
    this.value = value;
  }

  @JsonCreator
  public static TransType fromValue(final String value) throws BadRequestException {
    return Arrays.stream(TransType.values())
        .filter(role -> role.getValue().equalsIgnoreCase(value))
        .findFirst()
        .orElseThrow(
            () -> new BadRequestException("Trans. Type with value '" + value + "' not found"));
  }

  @JsonValue
  public String getValue() {
    return this.value;
  }
}
