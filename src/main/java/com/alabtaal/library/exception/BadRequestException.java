package com.alabtaal.library.exception;

public class BadRequestException extends AppException {

  /**
   *
   */


  public BadRequestException(final String message) {
    super(message);
  }

  public BadRequestException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
