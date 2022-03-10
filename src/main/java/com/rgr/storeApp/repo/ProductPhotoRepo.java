package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.product.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPhotoRepo extends JpaRepository<ProductPhoto, Long> {
}
