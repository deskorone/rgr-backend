package com.rgr.storeApp.service.admin;


import com.rgr.storeApp.dto.product.ProductLiteResponse;
import com.rgr.storeApp.dto.userProfile.UserProfileInfo;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.product.Store;
import com.rgr.storeApp.repo.StoreRepo;
import com.rgr.storeApp.repo.UsersRepo;
import com.rgr.storeApp.service.find.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final FindService findService;
    private final UsersRepo usersRepo;
    private final StoreRepo storeRepo;


    @Autowired
    public AdminService(FindService findService, UsersRepo usersRepo, StoreRepo storeRepo) {
        this.findService = findService;
        this.usersRepo = usersRepo;
        this.storeRepo = storeRepo;
    }


    @Transactional
    public List<ProductLiteResponse> getStore(String email){
        Store store = storeRepo.getByEmail(email).orElseThrow(()->new NotFound("Store not found"));
        return  store
                .getProducts()
                .stream()
                .map(ProductLiteResponse::build)
                .collect(Collectors.toList());
    }


    public UserProfileInfo getProfile(String email){
        return UserProfileInfo.build(findService.getUser(email));
    }


    public void addBan(Long id){
        User user = findService.getById(id);
        if(!user.isLocked()) {
            user.setLocked(true);
            usersRepo.save(user);
        }
    }

    public void removeBan(Long id){
        User user = findService.getById(id);
        if(user.isLocked()) {
            user.setLocked(false);
            usersRepo.save(user);
        }
    }





}
