package com.cashregister.sale.repository;

import com.cashregister.sale.model.SalesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISalesRepository extends JpaRepository<SalesInfo, Long> {
}
