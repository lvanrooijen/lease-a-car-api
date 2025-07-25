package com.lvr.lease_a_car.entities.user.dto;

import com.lvr.lease_a_car.utils.annotation.role.HasRole;
import com.lvr.lease_a_car.utils.constants.ExceptionMessages;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

/**
 * DTO used for patching a User object
 *
 * <p>Fields may contain null values
 *
 * @param email
 * @param firstName
 * @param lastName
 * @param role uses {@link HasRole} validator
 */
public record PatchUser(
    @Email(message = "Invalid email") String email,
    @Length(min = 3, max = 50, message = ExceptionMessages.FIRST_NAME_INVALID_LENGTH)
        String firstName,
    @Length(min = 3, max = 100, message = ExceptionMessages.LAST_NAME_INVALID_LENGTH)
        String lastName,
    @HasRole String role) {}
