package com.alabtaal.library.exception;

public class AppException extends Exception {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public AppException(final String message) {
    super(message);
  }

  public AppException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
