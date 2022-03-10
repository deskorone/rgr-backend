package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.basket.BuyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyHistoryRepo extends JpaRepository<BuyHistory, Long> {
}
