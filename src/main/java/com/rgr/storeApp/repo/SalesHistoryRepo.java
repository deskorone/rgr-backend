package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.basket.SellHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SalesHistoryRepo extends JpaRepository<SellHistory, Long> {




}
