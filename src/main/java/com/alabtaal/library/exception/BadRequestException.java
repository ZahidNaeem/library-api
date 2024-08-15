package com.alabtaal.library.exception;

public class BadRequestException extends AppException {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public BadRequestException(final String message) {
    super(message);
  }

  public BadRequestException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
