package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.ConfirmationToken;
import com.rgr.storeApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConfirmationTokenRepo extends JpaRepository<ConfirmationToken, Long> {

    @Query(value = "select t from ConfirmationToken t where t.token = :token")
    Optional<ConfirmationToken> findByToken(@Param("token") String token);

}
