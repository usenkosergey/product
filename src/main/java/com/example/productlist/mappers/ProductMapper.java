package com.example.productlist.mappers;

import com.example.productlist.api.ProductDTO;
import com.example.productlist.entities.ProductEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO oneProductEntityToDTO(ProductEntity productEntity);

    List<ProductDTO> productDTOList(List<ProductEntity> productEntityList);

}
