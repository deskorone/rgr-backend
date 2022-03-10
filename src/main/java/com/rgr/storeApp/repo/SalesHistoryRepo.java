package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.basket.SellHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesHistoryRepo extends JpaRepository<SellHistory, Long> {
}
