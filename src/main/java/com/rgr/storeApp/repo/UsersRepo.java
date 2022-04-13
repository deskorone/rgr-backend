package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersRepo extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users u inner join user_profile as up on u.user_profile_id = up.id WHERE email=:email ",nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM user u where u.id = :id", nativeQuery = true)
    Optional<User> getByUserById(@Param("id") Long id);

    boolean existsByEmail(String email);


}
