package com.alabtaal.library.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends Exception {

  /**
   *
   */

  private final String resourceName;
  private final String fieldName;
  private final Object fieldValue;

  public ResourceNotFoundException(final String resourceName, final String fieldName,
      final Object fieldValue) {
    super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }

}
