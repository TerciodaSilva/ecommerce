package com.product.security.dto;

import com.product.security.model.Role;
import jakarta.validation.constraints.NotEmpty;

import static com.product.security.dto.exceptions.InvalidEmailException.validateEmail;
import static com.product.security.dto.exceptions.InvalidPasswordException.validatePassword;

public record RegisterDTO(@NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty String email, @NotEmpty String password, @NotEmpty Role role ) {
    public RegisterDTO {
        // validateEmail(email);
        validatePassword(password);
    }
}
