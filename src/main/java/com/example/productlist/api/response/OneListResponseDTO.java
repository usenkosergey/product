package com.example.productlist.api.response;

import com.example.productlist.api.ProductDTO;
import lombok.Data;

import java.util.List;

@Data
public class OneListResponseDTO {
    private String name;
    private List<ProductDTO> products;
}
