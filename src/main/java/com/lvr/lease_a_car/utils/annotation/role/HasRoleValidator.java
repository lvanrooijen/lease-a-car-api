package com.lvr.lease_a_car.utils.annotation.role;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validates a Role
 *
 * <p>Roles: BROKER, EMPLOYEE
 */
public class HasRoleValidator implements ConstraintValidator<HasRole, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isBlank()) {
      return true;
    }

    setErrorMessage(context, "Role must be BROKER or EMPLOYEE");

    if ("BROKER".equals(value)) {
      return true;
    }

    if ("EMPLOYEE".equals(value)) {
      return true;
    }

    return false;
  }

  private void setErrorMessage(ConstraintValidatorContext context, String errorMessage) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
  }
}
