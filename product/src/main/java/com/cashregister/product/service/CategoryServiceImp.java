package com.cashregister.product.service;

import com.cashregister.product.exception.CategoryNotFound;
import com.cashregister.product.model.Category;
import com.cashregister.product.repository.ICategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class CategoryServiceImp implements ICategoryService {

    final ICategoryRepository categoryRepository;

    @Override
    public void deleteCategory(long id) {
        log.info("CategoryServiceImp: deleteCategory method called");
        Category category = categoryRepository.findCategoryByIdAndDeletedFalse(id).orElseThrow(() -> new CategoryNotFound("Category Not Found"));
        category.setDeleted(true);
        categoryRepository.save(category);
        log.info("CategoryServiceImp: deleteCategory method completed successfully for category = {}", category);

    }

    @Override
    public List<Category> getAllCategory() {
        log.info("CategoryServiceImp: getAllCategory method called");
        List<Category> categories = categoryRepository.findAll();
        log.info("CategoryServiceImp: getAllCategory method returned {} categories", categories.size());
        return categories;
    }

    @Override
    public void addCategory(Category category) {
        log.info("CategoryServiceImp: addCategory method called with category = {}", category);

        try {
            boolean isExist = categoryRepository.existsByCategoryName(category.getCategoryName());
            if (isExist) {
                log.warn("CategoryServiceImp: The category name '{}' already exists", category.getCategoryName());
                throw new RuntimeException("The category name already exists");
            }

            Category newCategory = new Category();
            newCategory.setCategoryName(category.getCategoryName());
            categoryRepository.save(newCategory);
            log.info("CategoryServiceImp: addCategory method completed successfully for category = {}", category);
        } catch (Exception e) {
            log.error("CategoryServiceImp: Error in addCategory method for category = {}. Error: {}", category, e.getMessage());
            throw e;
        }
    }

    @Override
    public Category getById(long id) {

        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFound("Category Not Found"));

    }
}
