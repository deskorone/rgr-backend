package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.ERole;
import com.rgr.storeApp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(ERole role);
}
