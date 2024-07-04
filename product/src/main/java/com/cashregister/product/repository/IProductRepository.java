package com.cashregister.product.repository;


import com.cashregister.product.model.Category;
import com.cashregister.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * from product where product.deleted=false",nativeQuery = true)
List<Product> getProductListByDeletedFalse();

    Optional<Product>findProductByIdAndDeletedFalse(long id);
}
