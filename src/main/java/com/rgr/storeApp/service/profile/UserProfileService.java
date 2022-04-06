package com.rgr.storeApp.service.profile;


import com.rgr.storeApp.dto.userProfile.*;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.delivery.AwaitingList;
import com.rgr.storeApp.repo.AwaitingListRepo;
import com.rgr.storeApp.repo.UsersRepo;
import com.rgr.storeApp.service.find.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserProfileService {

    private final UsersRepo usersRepo;;
    private final AwaitingListRepo awaitingListRepo;
    private final FindService findService;

    @Autowired
    public UserProfileService(UsersRepo usersRepo, AwaitingListRepo awaitingListRepo, AwaitingListRepo awaitingListRepo1, FindService findService) {
        this.usersRepo = usersRepo;
        this.awaitingListRepo = awaitingListRepo1;
        this.findService = findService;
    }


    public UserGeneralProfileResponse getGeneral(){
        User user = findService.getUser(findService.getEmailFromAuth());
        return UserGeneralProfileResponse.build(user);
    }

    public AwaitingListDto getAwaitings(){
        return  AwaitingListDto
                .build(findService.getUser(findService.getEmailFromAuth()).getUserProfile()
                        .getAwaitingList());
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

    public UserProfileInfo getProfile(Long id){
        return UserProfileInfo.build(findService.getById(id));
    }


    public BuyHistoryDto getBuyHistory(){
        User user = findService.getUser(findService.getEmailFromAuth());
        return BuyHistoryDto.build(user.getUserProfile().getBuyHistory());
    }

    public AwaitingListDto refreshDeliveries(){
        User user = findService.getUser(findService.getEmailFromAuth());
        AwaitingList awaitingList = user.getUserProfile().getAwaitingList();
        awaitingList.getDeliveries().forEach(e->{
            if(e.getArrival().isBefore(LocalDateTime.now())){
                awaitingList.deleteDelivery(e);
            }
        });
        return AwaitingListDto.build(awaitingListRepo.save(awaitingList));

    }

}
