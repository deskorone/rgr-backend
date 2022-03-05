package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.ERole;
import com.rgr.storeApp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface RolesRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(ERole role);
}
