package com.felix.projects.salespoint.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.felix.projects.salespoint.utils.SubError;
import com.felix.projects.salespoint.utils.ValidationError;
import lombok.Data;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** The type Error response. */
@Data
public class ErrorResponse {

  private HttpStatus status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;

  private String message;

  private String debugMessage;

  private List<SubError> subErrors;

  private ErrorResponse() {
    timestamp = LocalDateTime.now();
  }

  /**
   * Instantiates a new Error response.
   *
   * @param status the status
   */
  public ErrorResponse(HttpStatus status) {
    this();
    this.status = status;
  }

  /**
   * Instantiates a new Error response.
   *
   * @param status the status
   * @param e the e
   */
  public ErrorResponse(HttpStatus status, Throwable e) {
    this();
    this.status = status;
    this.message = "unexpected error";
    this.debugMessage = e.getLocalizedMessage();
  }

  /**
   * Instantiates a new Error response.
   *
   * @param status the status
   * @param message the message
   * @param e the e
   */
  public ErrorResponse(HttpStatus status, String message, Throwable e) {
    this();
    this.status = status;
    this.message = message;
    this.debugMessage = e.getLocalizedMessage();
  }

  private void addSubError(SubError error) {
    if (subErrors == null) {
      subErrors = new ArrayList<>();
    }
    subErrors.add(error);
  }

  private void addValidationError(
      String object, String field, Object rejectedValue, String message) {
    addSubError(new ValidationError(object, field, rejectedValue, message));
  }

  private void addValidationError(String object, String message) {
    addSubError(new ValidationError(object, message));
  }

  private void addValidationError(FieldError error) {
    this.addValidationError(
        error.getObjectName(),
        error.getField(),
        error.getRejectedValue(),
        error.getDefaultMessage());
  }

  /**
   * The Add validation errors. @param errors the errors @param errors the errors @param errors the
   * errors
   *
   * @param errors the errors
   */
  public <T> void addValidationErrors(List<T> errors) {

    errors.forEach(
        error -> {
          if (error instanceof FieldError) {
            this.addValidationError((FieldError) error);
          }
          if (error instanceof ObjectError) {
            this.addValidationError((ObjectError) error);
          }
        });
  }

  private void addValidationError(ObjectError error) {
    this.addValidationError(error.getObjectName(), error.getDefaultMessage());
  }

  private void addValidationError(ConstraintViolation<?> constraintViolation) {
    this.addValidationError(
        constraintViolation.getRootBeanClass().getSimpleName(),
        ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString(),
        constraintViolation.getInvalidValue(),
        constraintViolation.getMessage());
  }

  /**
   * Add validation errors.
   *
   * @param constraintViolations the constraint violations
   */
  public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
    constraintViolations.forEach(this::addValidationError);
  }
}
