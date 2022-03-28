package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.product.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM reviews r where r.product_id = :#{#product.id}", nativeQuery = true)
    List<Review> findByProduct(@Param("product")Product product);

    @Query(value = "select avg (r.rating) from  Review r where r.product.id = :#{#product.id}")
    Double getRating(@Param("product") Product product);

}
