package com.felix.projects.salespoint.validator;

import com.felix.projects.salespoint.entities.RoleEntity;
import com.felix.projects.salespoint.repository.RoleRepository;
import com.felix.projects.salespoint.validators.RoleValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ActiveProfiles("roleValidatorTest")
@RunWith(MockitoJUnitRunner.class)
public class RoleValidatorUnitTests {

  @InjectMocks private RoleValidator roleValidator;

  @Mock private RoleRepository roleRepository;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    roleValidator = new RoleValidator(roleRepository);
  }

  @Test
  public void supportsTest() {
    RoleEntity roleEntity = new RoleEntity();

    assertTrue(roleValidator.supports(roleEntity.getClass()));
  }

  @Test
  public void validateTestIsValid() {
    RoleEntity roleEntity = new RoleEntity();
    roleEntity.setId(1);

    when(roleRepository.existsById(1)).thenReturn(true);

    Errors errors = new BeanPropertyBindingResult(roleEntity, "roleEntity");

    roleValidator.validate(roleEntity, errors);

    assertFalse(errors.hasErrors());
  }

  @Test
  public void validateTestIsNotValid() {
    RoleEntity roleEntity = new RoleEntity();
    roleEntity.setId(1);

    when(roleRepository.existsById(1)).thenReturn(false);

    Errors errors = new BeanPropertyBindingResult(roleEntity, "roleEntity");

    roleValidator.validate(roleEntity, errors);

    assertTrue(errors.hasErrors());
  }
}
