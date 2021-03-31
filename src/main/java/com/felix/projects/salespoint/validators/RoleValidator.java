package com.felix.projects.salespoint.validators;

import com.felix.projects.salespoint.entities.RoleEntity;
import com.felix.projects.salespoint.repository.RoleRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/** The type Role validator. */
public class RoleValidator implements Validator {

  private final RoleRepository roleRepository;

  /**
   * Instantiates a new Owner validator.
   *
   * @param roleRepository the role repository
   */
  public RoleValidator(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return RoleEntity.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {

    RoleEntity roleEntity = (RoleEntity) o;

    if (roleEntity.getId() == null || !roleRepository.existsById(roleEntity.getId())) {
      errors.rejectValue("role", "role does not exist.");
    }
  }
}
