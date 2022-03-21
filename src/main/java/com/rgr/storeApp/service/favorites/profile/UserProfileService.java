package com.rgr.storeApp.service.favorites.profile;


import com.rgr.storeApp.dto.userProfile.BasketDto;
import com.rgr.storeApp.dto.userProfile.UserGeneralProfileResponse;
import com.rgr.storeApp.dto.userProfile.UserInfoRequest;
import com.rgr.storeApp.dto.userProfile.UserProfileInfo;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.delivery.AwaitingList;
import com.rgr.storeApp.repo.AwaitingListRepo;
import com.rgr.storeApp.repo.UsersRepo;
import com.rgr.storeApp.service.find.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    private final UsersRepo usersRepo;;
    private final FindService findService;

    @Autowired
    public UserProfileService(UsersRepo usersRepo, AwaitingListRepo awaitingListRepo, FindService findService) {
        this.usersRepo = usersRepo;
        this.findService = findService;
    }


    public UserGeneralProfileResponse getGeneral(){
        User user = findService.getUser(findService.getEmailFromAuth());
        return UserGeneralProfileResponse.build(user);
    }

    public AwaitingList getAwaitings(){
        User user = findService.getUser(findService.getEmailFromAuth());
        return  user.getUserProfile().getAwaitingList();
    }

    public BasketDto getBasket(){
        User user = findService.getUser(findService.getEmailFromAuth());
        return BasketDto.build(user.getUserProfile().getBasket());
    }


    @Transactional
    public UserProfileInfo updateInfo(UserInfoRequest userInfoRequest){
        User user = findService.getUser(findService.getEmailFromAuth());
        user.setLastname(userInfoRequest.getLastname());
        user.getUserProfile().setIndex(userInfoRequest.getIndex());
        user.getUserProfile().setTown(userInfoRequest.getTown());
        return UserProfileInfo.build(usersRepo.save(user));
    }





}
