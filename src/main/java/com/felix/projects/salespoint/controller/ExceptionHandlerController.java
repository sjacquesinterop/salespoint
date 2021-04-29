package com.felix.projects.salespoint.controller;

import com.felix.projects.salespoint.dto.ErrorResponse;
import com.felix.projects.salespoint.exceptions.CustomValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

/** The type Exception handler controller. */
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

  private ResponseEntity<Object> buildResponseEntity(ErrorResponse error) {
    return new ResponseEntity<>(error, error.getStatus());
  }

  /**
   * Handle entity not found response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException e) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e);
    errorResponse.setMessage(e.getMessage());
    return buildResponseEntity(errorResponse);
  }

  /**
   * Handle data integrity violation response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException e) {
    if (e.getCause() instanceof ConstraintViolationException) {
      return buildResponseEntity(
          new ErrorResponse(HttpStatus.CONFLICT, "Database error", e.getCause()));
    }
    return buildResponseEntity(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e));
  }

  /**
   * Handle constraint violation response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler(javax.validation.ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolation(
      javax.validation.ConstraintViolationException e) {

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e);
    errorResponse.setMessage("Validation error");
    errorResponse.addValidationErrors(e.getConstraintViolations());
    return buildResponseEntity(errorResponse);
  }

  /**
   * Handle illegal argument exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e);
    errorResponse.setMessage(e.getMessage());
    return buildResponseEntity(errorResponse);
  }

  /**
   * Handle validation exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler(CustomValidationException.class)
  public ResponseEntity<Object> handleValidationException(CustomValidationException e) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e);
    errorResponse.addValidationErrors(e.getErrors().getAllErrors());
    return buildResponseEntity(errorResponse);
  }
}
