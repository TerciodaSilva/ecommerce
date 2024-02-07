package com.product.security.dto.exceptions.handler;

import com.product.security.dto.exceptions.InvalidEmailException;
import com.product.security.dto.exceptions.InvalidPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class AuthenticationExceptionHandler {

    @ExceptionHandler(InvalidEmailException.class)
    public void handleInvalidEmailException(InvalidEmailException ex) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public void handleInvalidPasswordException(InvalidPasswordException ex) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}