package com.product.project.services;

import com.product.project.dto.CategoryDTO;
import com.product.project.entities.Category;
import com.product.project.repositories.CategoryRepository;
import com.product.project.services.exception.DatabaseException;
import com.product.project.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

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
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
