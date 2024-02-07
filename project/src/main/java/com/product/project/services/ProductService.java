package com.product.project.services;

import com.product.project.dto.ProductDTO;
import com.product.project.entities.Category;
import com.product.project.entities.Product;
import com.product.project.repositories.CategoryRepository;
import com.product.project.repositories.ProductRepository;
import com.product.project.services.exceptions.ProductExceptionNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(ProductExceptionNotFound::new);
    }

    @Transactional(readOnly = true)
    public Page<Product> getProductsPageable(PageRequest pageRequest) {
        Page<Product> page = productRepository.findAll(pageRequest);
        productRepository.findProductsPageable(page.stream().collect(Collectors.toList()));
        return page;
    }

    @Transactional
    public Product insert(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImgUrl(productDTO.getImgUrl());
        product.setDate(Instant.now());

        List<Category> categories = categoryRepository.findAllById(productDTO.getCategoryIds());
        product.setCategories(new HashSet<>(categories));

        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.findById(id).orElseThrow(ProductExceptionNotFound::new);
        productRepository.deleteById(id);
    }

    public Product update(Long id, ProductDTO product) {
        productRepository.findById(id).orElseThrow(ProductExceptionNotFound::new);

        Product entity = productRepository.getReferenceById(id);
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setImgUrl(product.getImgUrl());
        entity.setPrice(product.getPrice());
        entity.setDate(Instant.now());

        List<Category> categories = categoryRepository.findAllById(product.getCategoryIds());
        entity.setCategories(new HashSet<>(categories));
        return productRepository.save(entity);
    }
}
