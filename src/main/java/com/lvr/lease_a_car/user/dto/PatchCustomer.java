package com.lvr.lease_a_car.user.dto;

import com.lvr.lease_a_car.utils.constants.ExceptionMessages;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record PatchCustomer(
    @Email(message = "Invalid email") String email,
    @Length(min = 3, max = 50, message = ExceptionMessages.FIRST_NAME_INVALID_LENGTH)
        String firstName,
    @Length(min = 3, max = 100, message = ExceptionMessages.LAST_NAME_INVALID_LENGTH)
        String lastName) {}
