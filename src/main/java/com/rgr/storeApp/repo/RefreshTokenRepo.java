package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.RefreshToken;
import com.rgr.storeApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<List<RefreshToken>> findAllByUser(User user);

    void deleteAllByUser(User user);

}
