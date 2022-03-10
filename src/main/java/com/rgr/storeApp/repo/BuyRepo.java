package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.basket.Buy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyRepo extends JpaRepository<Buy, Long> {
}
