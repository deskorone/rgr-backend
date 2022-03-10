package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.delivery.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepo extends JpaRepository<Delivery, Long> {
}
