package com.alabtaal.library.exception;

public class AppException extends Exception {

  /**
   *
   */


  public AppException(final String message) {
    super(message);
  }

  public AppException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
