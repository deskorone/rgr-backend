package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.product.Store;
import com.rgr.storeApp.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepo extends JpaRepository<Store, Long> {


    @Query(value = "select s.* from store as s inner join users as u on s.user_id = u.id where u.email = :email", nativeQuery = true)
    Optional<Store> getByEmail(@Param("email") String email);

}
