package com.alabtaal.library.enumeration;

import com.alabtaal.library.exception.BadRequestException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum RoleName {
  ROLE_ADMIN("admin"),
  ROLE_USER("user");

  private final String value;

  RoleName(final String value) {
    this.value = value;
  }

  @JsonCreator
  public static RoleName fromValue(final String value) throws BadRequestException {
    return Arrays.stream(RoleName.values())
        .filter(role -> role.getValue().equalsIgnoreCase(value))
        .findFirst()
        .orElseThrow(
            () -> new BadRequestException("Role with value '" + value + "' not found"));
  }

  @JsonValue
  public String getValue() {
    return this.value;
  }
}
