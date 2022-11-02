package com.example.productlist.controllers;

import com.example.productlist.api.response.ResponseStatus;
import com.example.productlist.api.request.ListRequestDTO;
import com.example.productlist.api.request.Product2ListTDO;
import com.example.productlist.api.ProductDTO;
import com.example.productlist.services.ListService;
import com.example.productlist.services.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api")
public class Controller {

    private final ProductService productService;
    private final ListService listService;

    public Controller(ProductService productService, ListService listService) {
        this.productService = productService;
        this.listService = listService;
    }

    @PostMapping("/new_product")
    @ApiOperation(value = "Добавить новый продукт", notes = "Имя продукта ОБЯЗАТЕЛЬНО, остальное нет")
    public ResponseEntity<ResponseStatus> addNewProduct(@RequestBody(required = false) ProductDTO productDTO) {
        log.info("/new_product - {}", productDTO);
        return ResponseEntity.ok(productService.saveNewProduct(productDTO));
    }

    @PostMapping("/new_list")
    public ResponseEntity<ResponseStatus> addNewList(@RequestBody(required = false)  ListRequestDTO listRequestDTO) {
        return ResponseEntity.ok(listService.saveNewList(listRequestDTO));
    }

    @PutMapping("/add_list_to_product/")
    public ResponseEntity<ResponseStatus> addListToProduct(@RequestBody(required = false) Product2ListTDO product2ListTDO) {
        return ResponseEntity.ok(productService.addList2Product(product2ListTDO));
    }

    @GetMapping("/lists")
    public ResponseEntity<?> getAllLists() {
        return ResponseEntity.ok(listService.getAll());
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
}
