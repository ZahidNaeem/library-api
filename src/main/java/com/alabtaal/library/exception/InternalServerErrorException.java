package com.alabtaal.library.exception;

public class InternalServerErrorException extends AppException {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public InternalServerErrorException(final String message) {
    super(message);
  }

  public InternalServerErrorException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
