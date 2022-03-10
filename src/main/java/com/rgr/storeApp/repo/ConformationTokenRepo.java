package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConformationTokenRepo extends JpaRepository<ConfirmationToken, Long> {
}
