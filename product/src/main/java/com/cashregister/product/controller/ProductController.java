package com.cashregister.product.controller;

import com.cashregister.product.model.Category;
import com.cashregister.product.model.Product;
import com.cashregister.product.model.dto.ProductWithCategoryDto;
import com.cashregister.product.service.ProductServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    final ProductServiceImp productService;

    public ProductController(ProductServiceImp productService) {
        this.productService = productService;
    }


    @PostMapping("save")
    public ResponseEntity<ProductWithCategoryDto>saveProduct(@RequestBody ProductWithCategoryDto productDto )
    {

        productService.saveProduct(productDto);
        return ResponseEntity.ok(productDto);


    }

    @GetMapping("getAll")
    public ResponseEntity<List<Product>>saveProduct( )
    {
        List<Product> productList=productService.getAllProduct();
        return  new ResponseEntity<>(productList,HttpStatus.OK);
    }
    @GetMapping("getProductById/{id}")
    public ResponseEntity<Product>getProductById( @PathVariable("id") long id)
    {
        Product product=productService.getProductVById(id);
        return  new ResponseEntity<>(product,HttpStatus.OK);
    }




}
