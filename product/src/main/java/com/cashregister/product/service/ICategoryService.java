package com.cashregister.product.service;

import com.cashregister.product.model.Category;

import java.util.List;

public interface ICategoryService {
    void deleteCategory(long id);

    List<Category> getAllCategory();

    void addCategory(Category category);

    Category getById(long id);
}

