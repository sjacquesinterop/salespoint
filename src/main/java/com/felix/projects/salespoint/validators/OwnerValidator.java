package com.felix.projects.salespoint.validators;

import com.felix.projects.salespoint.entities.UserEntity;
import com.felix.projects.salespoint.repository.UserRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/** The type Owner validator. */
public class OwnerValidator implements Validator {

  private final UserRepository userRepository;

  /**
   * Instantiates a new Owner validator.
   *
   * @param userRepository the user repository
   */
  public OwnerValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return UserEntity.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {

    UserEntity userEntity = (UserEntity) o;

    if (userEntity.getId() == null || !userRepository.existsById(userEntity.getId())) {
      errors.rejectValue(
          "id", "userEntity.getId()", "Owner with id " + userEntity.getId() + " does not exist.");
    }
  }
}
