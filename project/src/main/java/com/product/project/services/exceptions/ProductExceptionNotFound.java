package com.product.project.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductExceptionNotFound extends RuntimeException {
    public ProductExceptionNotFound(){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!");
    }
}
