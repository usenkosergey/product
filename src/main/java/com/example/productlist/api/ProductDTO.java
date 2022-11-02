package com.example.productlist.api;

import lombok.Data;

@Data
public class ProductDTO {
    private String name;
    private String description;
    private int kcal;
}
