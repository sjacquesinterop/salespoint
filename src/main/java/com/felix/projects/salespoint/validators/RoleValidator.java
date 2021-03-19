package com.felix.projects.salespoint.validators;

import com.felix.projects.salespoint.entities.UserEntity;
import com.felix.projects.salespoint.utils.EnumRoleValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/** The type Role validator. */
public class RoleValidator implements ConstraintValidator<EnumRoleValidator, UserEntity.Roles> {

  private UserEntity.Roles[] subset;

  @Override
  public void initialize(EnumRoleValidator constraint) {
    this.subset = constraint.anyOf();
  }

  @Override
  public boolean isValid(
      UserEntity.Roles roles, ConstraintValidatorContext constraintValidatorContext) {
    return roles == null || Arrays.asList(subset).contains(roles);
  }
}
