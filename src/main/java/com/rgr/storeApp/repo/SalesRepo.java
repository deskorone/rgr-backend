package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.profile.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesRepo extends JpaRepository<Sales, Long> {

    Page<Sales> findAllByProduct_Store_User_Email(String email, Pageable pageable);

}
