package com.product.project.services;

import com.product.project.dto.ProductDTO;
import com.product.project.entities.Category;
import com.product.project.entities.Product;
import com.product.project.repositories.CategoryRepository;
import com.product.project.repositories.ProductRepository;
import com.product.project.services.exception.DatabaseException;
import com.product.project.services.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!"));
    }

    @Transactional(readOnly = true)
    public Page<Product> getProductsPageable(PageRequest pageRequest) {
        try{
            Page<Product> page = productRepository.findAll(pageRequest);
            productRepository.findProductsPageable(page.stream().collect(Collectors.toList()));

            return page;
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional
    public Product insert(ProductDTO productDTO) {
        try {
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setImgUrl(productDTO.getImgUrl());
            product.setDate(Instant.now());

            List<Category> categories = categoryRepository.findAllById(productDTO.getCategoryIds());
            product.setCategories(new HashSet<>(categories));

            return productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            productRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Produto não encontrado!"));
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Product update(Long id, ProductDTO product) {
        try {
            Product entity = productRepository.getReferenceById(id);
            updateData(entity, product);
            return productRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado! ");
        }
    }

    private void updateData(Product entity, ProductDTO product) {
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setImgUrl(product.getImgUrl());
        entity.setPrice(product.getPrice());
        entity.setDate(Instant.now());

        List<Category> categories = categoryRepository.findAllById(product.getCategoryIds());
        entity.setCategories(new HashSet<>(categories));
    }
}
