package com.product.project.resources;

import com.product.project.dto.ProductDTO;
import com.product.project.entities.Product;
import com.product.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok().body(product);

    }

    @GetMapping
    public ResponseEntity<Page<Product>> find(@RequestParam Integer page,
                                              @RequestParam Integer linesPerPage,
                                              @RequestParam String direction,
                                              @RequestParam String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.by(Sort.Direction.fromString(direction), orderBy));
        Page<Product> list = productService.getProductsPageable(pageRequest);
        return ResponseEntity.ok(list);
    }


    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody ProductDTO productDTO) {
        Product product = productService.insert(productDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(product);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product product;
        product = productService.update(id, productDTO);
        return ResponseEntity.ok().body(product);
    }
}
