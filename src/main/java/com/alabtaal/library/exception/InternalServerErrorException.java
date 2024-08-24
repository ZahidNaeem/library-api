package com.alabtaal.library.exception;

public class InternalServerErrorException extends AppException {

  /**
   *
   */


  public InternalServerErrorException(final String message) {
    super(message);
  }

  public InternalServerErrorException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
