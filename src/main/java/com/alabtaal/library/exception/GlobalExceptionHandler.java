package com.alabtaal.library.exception;

import com.alabtaal.library.payload.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(value = BadRequestException.class)
  public ResponseEntity<ApiResponse<Boolean>> handleBadRequestException(final AppException ex) {
    LOG.debug("handleBadRequestException called");
    return new ResponseEntity<>(ApiResponse
        .<Boolean>builder()
        .success(false)
        .message(ex.getMessage())
        .entity(null)
        .build(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = InternalServerErrorException.class)
  public ResponseEntity<ApiResponse<Boolean>> handleInternalServerErrorException(
      final AppException ex) {
    LOG.debug("handleInternalServerErrorException called");
    return new ResponseEntity<>(ApiResponse
        .<Boolean>builder()
        .success(false)
        .message(ex.getMessage())
        .entity(null)
        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = ChildRecordFoundException.class)
  public ResponseEntity<ApiResponse<Boolean>> handleChildRecordFoundException(
      final AppException ex) {
    LOG.debug("handleChildRecordFoundException called");
    return new ResponseEntity<>(ApiResponse
        .<Boolean>builder()
        .success(false)
        .message(ex.getMessage())
        .entity(null)
        .build(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = DataIntegrityViolationException.class)
  public ResponseEntity<ApiResponse<Boolean>> handleDataIntegrityViolationException(
      final DataIntegrityViolationException ex) {
    LOG.debug("handleDataIntegrityViolationException called");
    return new ResponseEntity<>(ApiResponse
        .<Boolean>builder()
        .success(false)
        .message(Miscellaneous.getSqlExceptionMessage(ex))
        .entity(null)
        .build(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = ResourceNotFoundException.class)
  public ResponseEntity<ApiResponse<Boolean>> handleResourceNotFoundException(
      final AppException ex) {
    LOG.debug("handleResourceNotFoundException called");
    return new ResponseEntity<>(ApiResponse
        .<Boolean>builder()
        .success(false)
        .message(ex.getMessage())
        .entity(null)
        .build(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ApiResponse<Boolean>> handleException(final Exception ex) {
    LOG.debug("handleException called");
    return new ResponseEntity<>(ApiResponse
        .<Boolean>builder()
        .success(false)
        .message(ex.getMessage())
        .entity(null)
        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
