package com.cashregister.sale.repository;

import com.cashregister.sale.model.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShoppingListRepository extends JpaRepository<ShoppingList, Long> {
}
