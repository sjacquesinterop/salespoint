package com.felix.projects.salespoint.controller;

import com.felix.projects.salespoint.dto.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

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
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND);
    errorResponse.setMessage(e.getMessage());
    return buildResponseEntity(errorResponse);
  }

  /**
   * Handle data integrity violation response entity.
   *
   * @param e the e
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleDataIntegrityViolation(
      DataIntegrityViolationException e, WebRequest request) {
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

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST);
    errorResponse.setMessage("Validation error");
    errorResponse.addValidationErrors(e.getConstraintViolations());
    return buildResponseEntity(errorResponse);
  }

  /**
   * Handle method argument type mismatch response entity.
   *
   * @param e the e
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException e, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST);
    errorResponse.setMessage(
        String.format(
            "The parameter '%s' of value '%s' could not be converted to type '%s'",
            e.getName(), e.getValue(), e.getRequiredType().getSimpleName()));
    errorResponse.setDebugMessage(e.getMessage());
    return buildResponseEntity(errorResponse);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST);
    errorResponse.setMessage(e.getMessage());
    return buildResponseEntity(errorResponse);
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<Object> handleValidationException(ValidationException e) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE);
    errorResponse.setMessage("Error creating the entity.");
    return buildResponseEntity(errorResponse);
  }
}
