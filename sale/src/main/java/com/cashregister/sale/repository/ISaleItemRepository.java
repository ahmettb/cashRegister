package com.cashregister.sale.repository;

import com.cashregister.sale.model.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISaleItemRepository extends JpaRepository<SaleItem,Long> {
}
