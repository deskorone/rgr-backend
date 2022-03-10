package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserProfileRepo extends JpaRepository<UserProfile, Long> {


}
