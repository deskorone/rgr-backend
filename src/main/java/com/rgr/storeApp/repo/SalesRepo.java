package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.profile.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepo extends JpaRepository<Sales, Long> {

}
