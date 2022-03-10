package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.models.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BasketRepo extends JpaRepository<Basket, Long> {
    Optional<Basket> findByUserProfile(UserProfile userProfile);
}
