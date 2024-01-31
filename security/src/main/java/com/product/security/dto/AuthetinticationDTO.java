package com.product.security.dto;
import com.product.security.dto.exceptions.InvalidPasswordException;
import com.product.security.dto.exceptions.InvalidEmailException;

import static com.product.security.dto.exceptions.InvalidEmailException.validateEmail;
import static com.product.security.dto.exceptions.InvalidPasswordException.validatePassword;

public record AuthetinticationDTO(String email, String password) {
    public AuthetinticationDTO {
        validateEmail(email);
        validatePassword(password);
    }
}
