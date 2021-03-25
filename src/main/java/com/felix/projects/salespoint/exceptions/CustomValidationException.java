package com.felix.projects.salespoint.exceptions;

import lombok.Getter;
import org.springframework.validation.Errors;

import javax.validation.ValidationException;

@Getter
public class CustomValidationException extends ValidationException {

  private Errors errors;

  public CustomValidationException(String errorMessage, Errors errors) {
    super(errorMessage);
    this.errors = errors;
  }
}
