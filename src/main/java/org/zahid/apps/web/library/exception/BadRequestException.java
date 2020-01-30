package org.zahid.apps.web.library.exception;

public class BadRequestException extends AppException {

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}
