package com.felix.projects.salespoint.validator;

import com.felix.projects.salespoint.entities.RoleEntity;
import com.felix.projects.salespoint.entities.UserEntity;
import com.felix.projects.salespoint.repository.UserRepository;
import com.felix.projects.salespoint.validators.OwnerValidator;
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

@ActiveProfiles("OwnerValidatorTest")
@RunWith(MockitoJUnitRunner.class)
public class OwnerValidatorUnitTests {

  private final UserEntity testUser1 = new UserEntity();
  private final RoleEntity role = new RoleEntity();
  @InjectMocks private OwnerValidator ownerValidator;
  @Mock private UserRepository userRepository;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    ownerValidator = new OwnerValidator(userRepository);

    role.setId(1);

    testUser1.setId(1);
    testUser1.setName("Legolas");
    testUser1.setEmail("elf@gmail.com");
    testUser1.setPassword("!Password123");
    testUser1.setRole(role);
  }

  @Test
  public void supportsTest() {

    UserEntity user = new UserEntity();

    assertTrue(ownerValidator.supports(user.getClass()));
  }

  @Test
  public void validateTestIsValid() {
    UserEntity userEntity = new UserEntity();
    userEntity.setId(2);

    when(userRepository.existsById(2)).thenReturn(true);

    Errors errors = new BeanPropertyBindingResult(userEntity, "userEntity");

    ownerValidator.validate(userEntity, errors);

    assertFalse(errors.hasErrors());
  }
}
