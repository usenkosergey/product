package com.example.productlist.services;

import com.example.productlist.api.response.ResponseStatus;
import com.example.productlist.api.request.Product2ListTDO;
import com.example.productlist.api.ProductDTO;
import com.example.productlist.entities.ListEntity;
import com.example.productlist.entities.ProductEntity;
import com.example.productlist.mappers.ProductMapper;
import com.example.productlist.repositories.ListRepository;
import com.example.productlist.repositories.ProductRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ListRepository listRepository;
    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    public ProductService(ProductRepository productRepository, ListRepository listRepository) {
        this.productRepository = productRepository;
        this.listRepository = listRepository;
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> list = productRepository.findAll();
        return mapper.productDTOList(list);
    }

    public ResponseStatus addList2Product(Product2ListTDO product2ListTDO) {
        ResponseStatus<String> responseStatus = new ResponseStatus<>();
        responseStatus.setStatus("ERROR");
        String errorDescription = null;

        if (product2ListTDO == null) {
            errorDescription = "Нет данных для обработки";
        } else if (product2ListTDO.getProductID() == -1) {
            errorDescription = "Нет ID product";
        } else if (product2ListTDO.getListID() == -1) {
            errorDescription = "Нет ID list";
        }
        if (errorDescription != null) {
            responseStatus.setAnswer(errorDescription);
            return responseStatus;
        }

        Optional<ListEntity> listEntity = listRepository.findById(product2ListTDO.getListID());
        Optional<ProductEntity> productEntity = productRepository.findById(product2ListTDO.getProductID());
        if (!listEntity.isPresent()) {
            errorDescription = "Такого list не существует";
        }
        if (!productEntity.isPresent()) {
            errorDescription = "Такого product не существует";
        }
        if (errorDescription != null) {
            responseStatus.setAnswer(errorDescription);
            return responseStatus;
        }

        ProductEntity product = productEntity.get();
        product.setListEntity(listEntity.get());
        productRepository.save(product);
        responseStatus.setStatus("SUCCESS");
        responseStatus.setAnswer("Изменения внесены");
        return responseStatus;
    }

    public ResponseStatus saveNewProduct(ProductDTO productDTO) {
        ResponseStatus<String> responseStatus = new ResponseStatus<>();
        responseStatus.setStatus("ERROR");
        if (productDTO == null) {
            responseStatus.setAnswer("Нет продукта");
            return responseStatus;
        }
        if (productDTO.getName() == null || productDTO.getName().isEmpty()) {
            responseStatus.setAnswer("Нет имени продукта");
            return responseStatus;
        }

        ProductEntity newProduct = new ProductEntity();
        newProduct.setName(productDTO.getName());
        newProduct.setDescription(productDTO.getDescription());
        newProduct.setKcal(productDTO.getKcal());

        ProductEntity save = productRepository.save(newProduct);

        responseStatus.setStatus("SUCCESS");
        responseStatus.setAnswer("Продукт добавлен с ID " + save.getId());

        return responseStatus;
    }
}
