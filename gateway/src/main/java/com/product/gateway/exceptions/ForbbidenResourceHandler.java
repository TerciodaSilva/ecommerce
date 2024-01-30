package com.product.gateway.exceptions;

public class ForbbidenResourceHandler extends RuntimeException {
    public ForbbidenResourceHandler(String message) {
        super(message);
    }
}