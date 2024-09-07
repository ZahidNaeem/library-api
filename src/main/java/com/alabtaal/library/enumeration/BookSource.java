package com.alabtaal.library.enumeration;

import com.alabtaal.library.exception.BadRequestException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum BookSource {
  PURCHASED("purchased"),
  GIFTED("gifted"),
  OTHER("other");

  private final String value;

  BookSource(final String value) {
    this.value = value;
  }

  @JsonCreator
  public static BookSource fromValue(final String value) throws BadRequestException {
    return Arrays.stream(BookSource.values())
        .filter(role -> role.getValue().equalsIgnoreCase(value))
        .findFirst()
        .orElseThrow(
            () -> new BadRequestException("Book source with value '" + value + "' not found"));
  }

  @JsonValue
  public String getValue() {
    return this.value;
  }
}
