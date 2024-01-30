package com.product.gateway.exceptions.exception;

import com.product.gateway.exceptions.ForbbidenResourceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ForbbidenExceptionHandler {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ForbbidenResourceHandler> handler (ForbbidenResourceHandler ex) {
        return ResponseEntity.ok().body(ex);

    }
}
