package com.product.project.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CategoryExceptionNotFound extends RuntimeException {
    public CategoryExceptionNotFound(){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada!");
    }
}
