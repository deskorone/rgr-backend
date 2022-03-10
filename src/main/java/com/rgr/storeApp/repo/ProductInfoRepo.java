package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.product.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInfoRepo extends JpaRepository<ProductInfo, Long> {
}
