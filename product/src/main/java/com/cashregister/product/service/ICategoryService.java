package com.cashregister.product.service;

import com.cashregister.product.model.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> getAllCategory();

    void addCategory(Category category);
}

