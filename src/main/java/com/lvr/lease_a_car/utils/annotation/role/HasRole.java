package com.lvr.lease_a_car.utils.annotation.role;

import com.lvr.lease_a_car.utils.annotation.password.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Verifies if role appointed to a user exists */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface HasRole {
  String message() default "Password does not meet the requirements";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
