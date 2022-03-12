package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.product.Producer;
import com.rgr.storeApp.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductsRepo extends JpaRepository<Product, Long> {

    List<Product> findAllByProducer(Producer producer);

    Optional<Product> findById(Long id);
}
