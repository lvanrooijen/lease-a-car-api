package com.lvr.lease_a_car.utils.annotation.role;

import com.lvr.lease_a_car.entities.user.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

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
    List<String> roles = Arrays.stream(Role.values()).map(Enum::name).toList();

    setErrorMessage(context, "Invalid role, available roles: " + roles);

    return roles.contains(value);
  }

  private void setErrorMessage(ConstraintValidatorContext context, String errorMessage) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
  }
}
