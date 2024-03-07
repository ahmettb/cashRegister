package com.cashregister.product.service;

import com.cashregister.product.model.Category;
import com.cashregister.product.repository.ICategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImp implements ICategoryService{

    final ICategoryRepository categoryRepository;



    @Override
    public List<Category> getAllCategory() {

        return  categoryRepository.findAll();

    }

    @Override
    public void addCategory(Category category) {

        System.out.println("Category name"+category.getCategoryName());

        boolean isExist= categoryRepository.existsByCategoryName(category.getCategoryName());

        if(isExist)
        {

            throw  new RuntimeException("The category name already exist");
        }

        Category newCategory=new Category();
        newCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(newCategory);


    }
}
