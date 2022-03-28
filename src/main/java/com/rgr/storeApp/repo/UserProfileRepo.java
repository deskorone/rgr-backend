package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserProfileRepo extends JpaRepository<UserProfile, Long> {


    @Query(value = "select p from UserProfile p join fetch p.user where p.id = :id ")
    UserProfile findByIdFetchUser(@Param("id") Long id);

}
