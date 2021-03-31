package com.felix.projects.salespoint.exceptions;

import lombok.Getter;
import org.springframework.validation.Errors;

import javax.validation.ValidationException;

@Getter
public class CustomValidationException extends ValidationException {

  private final Errors errors;

  public CustomValidationException(String errorMessage, Errors errors) {
    super(errorMessage);
    this.errors = errors;
  }
}
