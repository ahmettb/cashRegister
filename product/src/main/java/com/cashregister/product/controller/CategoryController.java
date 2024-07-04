package com.cashregister.product.controller;

import com.cashregister.product.model.Category;
import com.cashregister.product.service.ICategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
@AllArgsConstructor
@Log4j2
public class CategoryController {

    final ICategoryService categoryService;

    @PostMapping(value = "add", consumes = {"application/json"})
    public ResponseEntity<String> saveCategory(@RequestBody Category category) {
        log.info("CategoryController: saveCategory method called with category = {}", category);

        try {
            categoryService.addCategory(category);
            log.info("CategoryController: saveCategory method completed successfully for category = {}", category);
            return ResponseEntity.status(HttpStatus.CREATED).body("Category: " + category.getCategoryName() + " added");
        } catch (Exception e) {
            log.error("CategoryController: Error in saveCategory method for category = {}. Error: {}", category, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("get-all")
    public ResponseEntity<List<Category>> getAllCategory() {
        log.info("CategoryController: getAllCategory method called");

        try {
            List<Category> categories = categoryService.getAllCategory();
            log.info("CategoryController: getAllCategory method returned {} categories", categories.size());
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            log.error("CategoryController: Error in getAllCategory method. Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("delete/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") long id) {
        log.info("CategoryController: deleteCategory method called");

        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfuly");

        } catch (Exception e) {
            log.error("CategoryController: Error in deleteCategory method. Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }


    }
}
