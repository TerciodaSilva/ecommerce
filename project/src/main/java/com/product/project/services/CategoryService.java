package com.product.project.services;

import com.product.project.dto.CategoryDTO;
import com.product.project.entities.Category;
import com.product.project.repositories.CategoryRepository;
import com.product.project.services.exceptions.CategoryExceptionNotFound;
import com.product.project.services.exceptions.ProductExceptionNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category insert(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.getName());
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        categoryRepository.findById(id).orElseThrow(CategoryExceptionNotFound::new);
        categoryRepository.deleteById(id);
    }
}
