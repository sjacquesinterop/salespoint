package com.felix.projects.salespoint.controller;

import com.felix.projects.salespoint.exceptions.CustomValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.Errors;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ActiveProfiles("exceptionHandlerController")
@RunWith(MockitoJUnitRunner.class)
public class ExceptionHandlerControllerUnitTests {

  @Mock Errors errors;
  @Mock DataIntegrityViolationException e;
  @Mock ConstraintViolationException constraintViolationException;
  @InjectMocks private ExceptionHandlerController exceptionHandlerController;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void handleEntityNotFoundTest() {

    EntityNotFoundException e = new EntityNotFoundException();

    ResponseEntity<Object> responseEntity = exceptionHandlerController.handleEntityNotFound(e);

    assertNotNull(responseEntity);
    assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
  }

  @Test
  public void handleDataIntegrityViolationWithoutConstraintViolationTest() {

    ResponseEntity<Object> responseEntity =
        exceptionHandlerController.handleDataIntegrityViolation(e);

    assertNotNull(responseEntity);
    assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Test
  public void handleDataIntegrityViolationWithConstraintViolationTest() {

    when(e.getCause()).thenReturn(constraintViolationException);

    ResponseEntity<Object> responseEntity =
        exceptionHandlerController.handleDataIntegrityViolation(e);

    assertNotNull(responseEntity);
    assertEquals(responseEntity.getStatusCode(), HttpStatus.CONFLICT);
  }

  @Test
  public void handleConstraintViolationTest() {

    ResponseEntity<Object> responseEntity =
        exceptionHandlerController.handleConstraintViolation(constraintViolationException);

    assertNotNull(responseEntity);
    assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
  }

  @Test
  public void handleIllegalArgumentExceptionTest() {
    IllegalArgumentException e = new IllegalArgumentException();

    ResponseEntity<Object> responseEntity =
        exceptionHandlerController.handleIllegalArgumentException(e);

    assertNotNull(responseEntity);
    assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
  }

  @Test
  public void handleValidationExceptionTest() {

    CustomValidationException e = new CustomValidationException("Validation error", errors);

    ResponseEntity<Object> responseEntity = exceptionHandlerController.handleValidationException(e);

    assertNotNull(responseEntity);
    assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
  }
}
