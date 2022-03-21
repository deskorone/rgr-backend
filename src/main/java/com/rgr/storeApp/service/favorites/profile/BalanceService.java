package com.rgr.storeApp.service.favorites.profile;


import com.rgr.storeApp.dto.BalanceRequest;
import com.rgr.storeApp.dto.userProfile.UserGeneralProfileResponse;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.profile.UserProfile;
import com.rgr.storeApp.repo.ProductsRepo;
import com.rgr.storeApp.repo.UserProfileRepo;
import com.rgr.storeApp.repo.UsersRepo;
import com.rgr.storeApp.service.find.FindService;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    private final UserProfileRepo userProfileRepo;
    private final UsersRepo usersRepo;
    private final ProductsRepo productsRepo;
    private final FindService findService;

    public BalanceService(UserProfileRepo userProfileRepo, UsersRepo usersRepo, ProductsRepo productsRepo, FindService findService) {
        this.userProfileRepo = userProfileRepo;
        this.usersRepo = usersRepo;
        this.productsRepo = productsRepo;
        this.findService = findService;
    }

    public UserGeneralProfileResponse addBalance(BalanceRequest balanceRequest){
        UserProfile userProfile = findService.getUser(findService.getEmailFromAuth()).getUserProfile();
        userProfile.setBalance(userProfile.getBalance() + balanceRequest.getMoney());
        userProfileRepo.save(userProfile);
        return UserGeneralProfileResponse.build(userProfile.getUser());
    }


}
