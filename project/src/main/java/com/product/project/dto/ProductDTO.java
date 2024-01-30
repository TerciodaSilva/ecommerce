package com.product.project.dto;

import com.product.project.entities.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class ProductDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private Double price;
    private String imgUrl;
    private Instant date;
    private Set<Long> categoryIds;


    public ProductDTO() {
    }

    public ProductDTO(String name, String description, Double price, String imgUrl, Set<Long> categoryIds) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.categoryIds = categoryIds;
    }

    public ProductDTO(Product product) {
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();
        imgUrl = product.getImgUrl();
        date = product.getDate();
    }
}


