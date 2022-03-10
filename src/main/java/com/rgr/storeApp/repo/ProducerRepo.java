package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.product.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProducerRepo extends JpaRepository<Producer, Long> {

    Optional<Producer> findByUser(User user);

}
