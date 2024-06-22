package com.cashregister.report.repository;

import com.cashregister.report.model.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<ShoppingList,Long> {
}
