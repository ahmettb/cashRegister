package com.cashregister.product.repository;

import com.cashregister.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category,Long> {


    boolean existsByCategoryName(String name);

}
