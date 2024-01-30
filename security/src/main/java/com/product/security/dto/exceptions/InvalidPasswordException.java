package com.product.security.dto.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }

    public static void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new InvalidPasswordException("Senha inválida: a senha deve ter pelo menos 8 caracteres");
        }
    }
}