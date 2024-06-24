package com.cashregister.product.controller;

import com.cashregister.product.model.Product;
import com.cashregister.product.model.dto.ProductWithCategoryDto;
import com.cashregister.product.service.ProductServiceImp;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
@Log4j2
public class ProductController {

    private final ProductServiceImp productService;

    public ProductController(ProductServiceImp productService) {
        this.productService = productService;
    }

    @PostMapping("add-product")
    public ResponseEntity<ProductWithCategoryDto> saveProduct(@RequestBody ProductWithCategoryDto productDto) {
        log.info("ProductController: saveProduct request received with body {} ", productDto.toString());
        productService.saveProduct(productDto);
        log.info("ProductController: saveProduct method completed.");
        return ResponseEntity.ok(productDto);
    }

    @PutMapping("update-stock/{id}/{count}")
    public ResponseEntity<?> updateStockCount(@PathVariable("id") long id, @PathVariable("count") int count) {
        log.info("ProductController: updateStockCount method called with id = {} and count = {}", id, count);
        productService.updateStock(id, count);
        log.info("ProductController: updateStockCount method completed.");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("delete-product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long id) {
        log.info("ProductController: deleteProduct method called with id = {}", id);
        productService.deleteProduct(id);
        log.info("ProductController: deleteProduct method completed.");
        return ResponseEntity.ok().build();
    }

    @GetMapping("get-all-product")
    public ResponseEntity<List<Product>> getAll() {
        log.info("ProductController: getAll method called.");
        List<Product> productList = productService.getAllProduct();
        log.info("ProductController: getAll method returned {} products.", productList.size());
        return ResponseEntity.ok(productList);
    }

    @GetMapping("get-product-byId/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        log.info("ProductController: getProductById method called with id = {}", id);
        Product product = productService.getProductById(id);
        log.info("ProductController: getProductById method returned product with id = {}", id);
        return ResponseEntity.ok(product);
    }
}
