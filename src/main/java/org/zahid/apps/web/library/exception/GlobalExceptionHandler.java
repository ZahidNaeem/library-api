package org.zahid.apps.web.library.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zahid.apps.web.library.payload.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger LOG = LogManager.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(value = BadRequestException.class)
  public ResponseEntity handleBadRequestException(AppException ex) {
    LOG.debug("handleBadRequestException called");
    return new ResponseEntity(ApiResponse
        .<Boolean>builder()
        .success(false)
        .message(ex.getMessage())
        .entity(null)
        .build(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = InternalServerErrorException.class)
  public ResponseEntity handleInternalServerErrorException(AppException ex) {
    LOG.debug("handleInternalServerErrorException called");
    return new ResponseEntity(ApiResponse
        .<Boolean>builder()
        .success(false)
        .message(ex.getMessage())
        .entity(null)
        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = ChildRecordFoundException.class)
  public ResponseEntity handleChildRecordFoundException(AppException ex) {
    LOG.debug("handleChildRecordFoundException called");
    return new ResponseEntity(ApiResponse
        .<Boolean>builder()
        .success(false)
        .message(ex.getMessage())
        .entity(null)
        .build(), HttpStatus.BAD_REQUEST);
  }

}
