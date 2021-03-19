package com.felix.projects.salespoint.utils;

import com.felix.projects.salespoint.entities.UserEntity;
import com.felix.projects.salespoint.validators.RoleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/** The interface Enum role validator. */
@Target({
  ElementType.METHOD,
  ElementType.ANNOTATION_TYPE,
  ElementType.CONSTRUCTOR,
  ElementType.PARAMETER,
  ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RoleValidator.class)
public @interface EnumRoleValidator {
  /**
   * Any of user entity . roles [ ].
   *
   * @return the user entity . roles [ ]
   */
  UserEntity.Roles[] anyOf();

  /**
   * Message string.
   *
   * @return the string
   */
  String message() default "must be any of {anyOf}";

  /**
   * Groups class [ ].
   *
   * @return the class [ ]
   */
  Class<?>[] groups() default {};

  /**
   * Payload class [ ].
   *
   * @return the class [ ]
   */
  Class<? extends Payload>[] payload() default {};
}
