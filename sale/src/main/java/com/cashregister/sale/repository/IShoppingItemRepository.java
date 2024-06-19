package com.cashregister.sale.repository;

import com.cashregister.sale.model.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShoppingItemRepository extends JpaRepository<ShoppingItem,Long> {
}
