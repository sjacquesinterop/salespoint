package com.felix.projects.salespoint.validators;

import com.felix.projects.salespoint.entities.UserEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TempRoleValidator implements Validator {

  private final UserEntity user;

  public TempRoleValidator(UserEntity user) {
    this.user = user;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return UserEntity.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {

    if (o == null) {
      errors.rejectValue("role", "role cannot be null.");
    }
    UserEntity userEntity = new UserEntity();

    for (UserEntity.Roles role :
        userEntity
            .getRole()
            .getDeclaringClass()
            .getEnumConstants()) { // TODO find a more efficient way to do the if statement, can't
                                   // do .contains?
      if (user.getRole() == role) {
        continue;
      } else {
        errors.rejectValue("role", "Role is not valid.");
      }
    }
  }
}
