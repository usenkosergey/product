package com.example.productlist.services;

import com.example.productlist.api.request.Product2ListTDO;
import com.example.productlist.api.response.ResponseStatus;
import com.example.productlist.entities.ListEntity;
import com.example.productlist.entities.ProductEntity;
import com.example.productlist.repositories.ListRepository;
import com.example.productlist.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @MockBean
    private ListRepository listRepositoryMock;
    @MockBean
    private ProductRepository productRepositoryMock;

    @Test
    void addList2ProductSave() {
        ListEntity listEntity = new ListEntity();
        ProductEntity productEntity = new ProductEntity();

        Product2ListTDO product2ListTDO = new Product2ListTDO();
        product2ListTDO.setProductID(1);
        product2ListTDO.setListID(1);


        Mockito.when(listRepositoryMock.findById(product2ListTDO.getListID())).thenReturn(Optional.of(listEntity));
        Mockito.when(productRepositoryMock.findById(product2ListTDO.getListID())).thenReturn(Optional.of(productEntity));

        ResponseStatus responseStatus = productService.addList2Product(product2ListTDO);

        Mockito.verify(productRepositoryMock, Mockito.times(1))
                .save(Mockito.argThat(productEntit -> productEntit.equals(productEntity)));

        assertEquals("SUCCESS", responseStatus.getStatus());
    }

    @Test
    void addList2ProductNull() {
        Product2ListTDO product2ListTDO = null;
        ResponseStatus responseStatus = productService.addList2Product(product2ListTDO);
        assertEquals("ERROR", responseStatus.getStatus());
    }

    @Test
    void addList2ProductErrProdID() {
        Product2ListTDO product2ListTDO = new Product2ListTDO();
        product2ListTDO.setListID(5);
        ResponseStatus responseStatus = productService.addList2Product(product2ListTDO);
        assertEquals("ERROR", responseStatus.getStatus());

    }


}