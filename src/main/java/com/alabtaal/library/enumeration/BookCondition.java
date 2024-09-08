package com.alabtaal.library.enumeration;

import com.alabtaal.library.exception.BadRequestException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum BookCondition {
  NEW("New"),
  OLD("Old");

  private final String value;

  BookCondition(final String value) {
    this.value = value;
  }

  @JsonCreator
  public static BookCondition fromValue(final String value) throws BadRequestException {
    return Arrays.stream(BookCondition.values())
        .filter(role -> role.getValue().equalsIgnoreCase(value))
        .findFirst()
        .orElseThrow(
            () -> new BadRequestException("Book condition with value '" + value + "' not found"));
  }

  @JsonValue
  public String getValue() {
    return this.value;
  }
}
