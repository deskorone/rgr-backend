package com.rgr.storeApp.service.favorites.profile;


import com.rgr.storeApp.dto.BalanceRequest;
import com.rgr.storeApp.dto.userProfile.UserGeneralProfileResponse;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.profile.UserProfile;
import com.rgr.storeApp.repo.ProductsRepo;
import com.rgr.storeApp.repo.UserProfileRepo;
import com.rgr.storeApp.repo.UsersRepo;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    private final UserProfileRepo userProfileRepo;
    private final UsersRepo usersRepo;
    private final ProductsRepo productsRepo;

    public BalanceService(UserProfileRepo userProfileRepo, UsersRepo usersRepo, ProductsRepo productsRepo) {
        this.userProfileRepo = userProfileRepo;
        this.usersRepo = usersRepo;
        this.productsRepo = productsRepo;
    }

    public UserGeneralProfileResponse addBalance(BalanceRequest balanceRequest, String email){
        UserProfile userProfile = usersRepo
                .findByEmail(email)
                .orElseThrow(()-> new NotFound("NOT FOUND user"))
                .getUserProfile();
        userProfile.setBalance(userProfile.getBalance() + balanceRequest.getMoney());
        userProfileRepo.save(userProfile);
        return UserGeneralProfileResponse.build(userProfile.getUser());
    }


}
