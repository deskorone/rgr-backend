package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.product.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Long> {
}
