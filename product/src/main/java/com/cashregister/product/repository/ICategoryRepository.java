package com.cashregister.product.repository;

import com.cashregister.product.model.Category;
import com.cashregister.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category,Long> {


    List<Category> findAllByDeletedFalse();
    boolean existsByCategoryName(String name);
    Optional<Category>findCategoryByIdAndDeletedFalse(long id);

}
