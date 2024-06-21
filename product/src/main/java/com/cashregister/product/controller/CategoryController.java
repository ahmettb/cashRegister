package com.cashregister.product.controller;

import com.cashregister.product.model.Category;
import com.cashregister.product.model.Product;
import com.cashregister.product.service.ICategoryService;
import com.cashregister.product.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
@AllArgsConstructor
public class CategoryController {

    final ICategoryService categoryService;


   @PostMapping(value = "save",consumes = {"application/json"})
    public ResponseEntity<String> saveCategory(@RequestBody Category category )
    {
        categoryService.addCategory(category);
        return ResponseEntity.ok("Success");


    }

    @GetMapping("getAll")

    public ResponseEntity<List<Category>> getAllCategory( )
    {
        return  new ResponseEntity<>(categoryService.getAllCategory(),HttpStatus.OK);
    }


}
