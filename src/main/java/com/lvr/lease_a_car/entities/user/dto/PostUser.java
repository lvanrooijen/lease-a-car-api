package com.lvr.lease_a_car.entities.user.dto;

import com.lvr.lease_a_car.utils.annotation.password.Password;
import com.lvr.lease_a_car.utils.annotation.role.HasRole;
import com.lvr.lease_a_car.utils.constants.ExceptionMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record PostUser(
    @NotBlank @Email(message = "Invalid email address") String email,
    @NotBlank @Password String password,
    @NotBlank @Length(min = 3, max = 50, message = ExceptionMessages.FIRST_NAME_INVALID_LENGTH)
        String firstName,
    @NotBlank @Length(min = 3, max = 50, message = ExceptionMessages.LAST_NAME_INVALID_LENGTH)
        String lastName,
    @NotBlank @HasRole String role) {}
