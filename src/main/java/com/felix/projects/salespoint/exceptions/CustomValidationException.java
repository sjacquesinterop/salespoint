package com.felix.projects.salespoint.exceptions;

import lombok.Getter;
import org.springframework.validation.Errors;

import javax.validation.ValidationException;
import java.io.Serializable;

@Getter
public class CustomValidationException extends ValidationException implements Serializable {

  private final Errors errors;

  public CustomValidationException(String errorMessage, Errors errors) {
    super(errorMessage);
    this.errors = errors;
  }
}
