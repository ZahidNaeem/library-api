package com.alabtaal.library.exception;

public class ChildRecordFoundException extends AppException {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public ChildRecordFoundException(String message) {
    super(message);
  }

  public ChildRecordFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
