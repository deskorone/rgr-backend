package com.rgr.storeApp.service.favorites.profile;


import com.rgr.storeApp.dto.userProfile.BasketDto;
import com.rgr.storeApp.dto.userProfile.UserGeneralProfileResponse;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.delivery.AwaitingList;
import com.rgr.storeApp.repo.AwaitingListRepo;
import com.rgr.storeApp.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UsersRepo usersRepo;;
    private final AwaitingListRepo awaitingListRepo;

    @Autowired
    public UserProfileService(UsersRepo usersRepo, AwaitingListRepo awaitingListRepo) {
        this.usersRepo = usersRepo;
        this.awaitingListRepo = awaitingListRepo;
    }


    public UserGeneralProfileResponse getGeneral(String email){
        User user = usersRepo.findByEmail(email).orElseThrow(()-> new NotFound("User not found"));
        return UserGeneralProfileResponse.build(user);
    }

    public AwaitingList getAwaitings(String email){
        User user = usersRepo.findByEmail(email).orElseThrow(()-> new NotFound("User not found"));
        return  user.getUserProfile().getAwaitingList();
    }

    public BasketDto getBasket(String email){
        User user = usersRepo.findByEmail(email).orElseThrow(()-> new NotFound("User not found"));
        return BasketDto.build(user.getUserProfile().getBasket());
    }





}
