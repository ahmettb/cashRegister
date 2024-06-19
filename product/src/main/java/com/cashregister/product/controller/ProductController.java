package com.cashregister.product.controller;

import com.cashregister.product.model.Product;
import com.cashregister.product.model.dto.ProductWithCategoryDto;
import com.cashregister.product.service.ProductServiceImp;
import lombok.extern.log4j.Log4j2;
import org.apache.http.protocol.HTTP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    final ProductServiceImp productService;

    private static final Logger logger= LogManager.getLogger(ProductController.class);

    public ProductController(ProductServiceImp productService) {
        this.productService = productService;
    }


    @PostMapping("add-product")
    public ResponseEntity<ProductWithCategoryDto>saveProduct(@RequestBody ProductWithCategoryDto productDto )
    {
        logger.info("Method called: saveProduct");
        productService.saveProduct(productDto);
        return ResponseEntity.ok(productDto);


    }

    @PutMapping("update-stock/{id}/{count}")
    public ResponseEntity<?>updateStockCount(@PathVariable("id")long id,@PathVariable("count")int count)
    {
        logger.info("Method called: updateStockCount");
        productService.updateStock(id,count);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("delete-product/{id}")
    public  ResponseEntity<?>deleteProduct(@PathVariable("id")long id)
    {
        logger.info("Method called: deleteProduct: id= "+id);
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();


    }


    @GetMapping("get-all-product")
    public ResponseEntity<List<Product>>getAll( )
    {
        logger.info("Method called: getAll");
        List<Product> productList=productService.getAllProduct();
        logger.info("Returning method success: getAll");

        return  new ResponseEntity<>(productList,HttpStatus.OK);
    }
        @GetMapping("get-product-byId/{id}")
    public ResponseEntity<Product>getProductById( @PathVariable("id") long id)
    {
        logger.info("Method called: getProductById : id = {}",id);
        Product product=productService.getProductVById(id);
        logger.info("Returning product with id = {}",id);
        return  new ResponseEntity<>(product,HttpStatus.OK);
    }




}
