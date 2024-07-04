package com.cashregister.product.service;

import com.cashregister.product.model.Category;
import com.cashregister.product.repository.ICategoryRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImpTest {
    @Mock
    ICategoryRepository categoryRepository;
    @InjectMocks
    ICategoryService categoryService;

    @Test
    void getAllCategory() {
    }

    @Test
    @Order(1)
    void addCategory() {



    }
}