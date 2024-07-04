package com.cashregister.sale.feignclient;

import com.cashregister.sale.config.FeignConif;
import com.cashregister.sale.model.Product;
import com.cashregister.sale.model.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product",url = "http://localhost:8000",path = "/api/product",configuration = FeignConif.class)
public interface ProductClient {


    @GetMapping("get-product-all-info/{id}")
     ResponseEntity<Product> getProductById(@PathVariable("id") long id);

    @PutMapping("update-stock/{id}/{count}")
    ResponseEntity<?>updateStock(@PathVariable("id") long id, @PathVariable("count")int count);
}
