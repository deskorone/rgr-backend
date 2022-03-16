package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.product.Store;
import com.rgr.storeApp.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepo extends JpaRepository<Store, Long> {

    Optional<Store> findByUser(User user);

    List<Product> findAllById(Long id);

}
