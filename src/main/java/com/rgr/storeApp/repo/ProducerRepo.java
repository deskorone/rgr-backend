package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.product.Producer;
import com.rgr.storeApp.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProducerRepo extends JpaRepository<Producer, Long> {

    Optional<Producer> findByUser(User user);

    Optional<List<Product>> findAllById(Long id);

}
