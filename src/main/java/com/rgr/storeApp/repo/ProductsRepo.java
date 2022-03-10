package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductsRepo extends JpaRepository<Product, Long> {
    
}
