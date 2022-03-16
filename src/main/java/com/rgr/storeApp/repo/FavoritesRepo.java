package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.product.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepo extends JpaRepository<Favorites, Long> {
}
