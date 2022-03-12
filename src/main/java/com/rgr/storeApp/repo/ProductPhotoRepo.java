package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.product.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductPhotoRepo extends JpaRepository<ProductPhoto, Long> {
    Optional<ProductPhoto> findByPath(String path);
}
