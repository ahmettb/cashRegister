package com.cashregister.sale.repository;

import com.cashregister.sale.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICampaignRepository extends JpaRepository<Campaign,Long> {
}
