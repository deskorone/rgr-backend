package com.rgr.storeApp.service.profile;


import com.rgr.storeApp.dto.userProfile.*;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.delivery.AwaitingList;
import com.rgr.storeApp.models.product.Store;
import com.rgr.storeApp.models.profile.Sales;
import com.rgr.storeApp.models.profile.StoreUpdateRequest;
import com.rgr.storeApp.repo.AwaitingListRepo;
import com.rgr.storeApp.repo.SalesRepo;
import com.rgr.storeApp.repo.StoreRepo;
import com.rgr.storeApp.repo.UsersRepo;
import com.rgr.storeApp.service.find.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileService {

    private final UsersRepo usersRepo;
    private final AwaitingListRepo awaitingListRepo;
    private final FindService findService;
    private final SalesRepo salesRepo;
    private final StoreRepo storeRepo;


    @Autowired
    public UserProfileService(UsersRepo usersRepo, AwaitingListRepo awaitingListRepo1, FindService findService, SalesRepo salesRepo, StoreRepo storeRepo) {
        this.usersRepo = usersRepo;
        this.awaitingListRepo = awaitingListRepo1;
        this.findService = findService;
        this.salesRepo = salesRepo;
        this.storeRepo = storeRepo;
    }


    public UserGeneralProfileResponse getGeneral(){
        User user = findService.getUser(findService.getEmailFromAuth());
        return UserGeneralProfileResponse.build(user);
    }


    public UserProfileInfo getProfileInfo(){
        return UserProfileInfo.build(findService.getUser(findService.getEmailFromAuth()));
    }

    public BasketDto getBasket(){
        User user = findService.getUser(findService.getEmailFromAuth());
        return BasketDto.build(user.getUserProfile().getBasket());
    }

    @Transactional
    public UserProfileInfo updateInfo(UserInfoRequest userInfoRequest){
        User user = findService.getUser(findService.getEmailFromAuth());
        user.setUsername(userInfoRequest.getUsername());
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

    @Transactional
    public AwaitingListDto refreshDeliveries(){
        AwaitingList awaitingList = awaitingListRepo.findAllByUserProfile_User_Email(findService.getEmailFromAuth());
        awaitingList.getDeliveries().forEach(e->{
            if(e.getArrival().isBefore(LocalDateTime.now())){
                awaitingList.deleteDelivery(e);
            }
        });
        return AwaitingListDto.build(awaitingListRepo.save(awaitingList));
    }

    public List<SalesDto> getSellHistory(int count, int size){
        Pageable pageable = PageRequest.of(count - 1, size);
        Page<Sales> sales = salesRepo.getSalesOnEmail(findService.getEmailFromAuth(), pageable);
        return sales.stream()
                .map(SalesDto::build)
                .collect(Collectors.toList());
    }

    @Transactional
    public Store updateStore(StoreUpdateRequest storeUpdateRequest){
        Store store = storeRepo.getByEmail(findService.getEmailFromAuth())
                .orElseThrow(()->new NotFound("Store not found"));
        store.setAddress(storeUpdateRequest.getAddress());
        store.setCountry(storeUpdateRequest.getCountry());
        store.setTown(storeUpdateRequest.getTown());
        return storeRepo.save(store);
    }

}
