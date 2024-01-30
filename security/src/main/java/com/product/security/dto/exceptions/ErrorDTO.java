package com.product.security.dto.exceptions;

import org.springframework.http.HttpStatus;

public record ErrorDTO(HttpStatus statusCode, String message) {
}
