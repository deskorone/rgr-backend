package com.rgr.storeApp.service.admin;


import com.rgr.storeApp.dto.product.ProductLiteResponse;
import com.rgr.storeApp.dto.userProfile.UserProfileInfo;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.repo.ProductInfoRepo;
import com.rgr.storeApp.repo.ReviewRepo;
import com.rgr.storeApp.repo.UsersRepo;
import com.rgr.storeApp.service.find.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final FindService findService;
    private final UsersRepo usersRepo;
    private final ReviewRepo reviewRepo;
    private final ProductInfoRepo productInfoRepo;

    @Autowired
    public AdminService(FindService findService, UsersRepo usersRepo, ReviewRepo reviewRepo, ProductInfoRepo productInfoRepo) {
        this.findService = findService;
        this.usersRepo = usersRepo;
        this.reviewRepo = reviewRepo;
        this.productInfoRepo = productInfoRepo;
    }

    public List<ProductLiteResponse> getStore(String email){
        User findUser = findService.getUser(email);
        return findUser.getStore()
                .getProducts()
                .stream()
                .map(e-> ProductLiteResponse.build(e, reviewRepo.getRating(e)))
                .collect(Collectors.toList());
    }

    public UserProfileInfo getProfile(String email){
        return UserProfileInfo.build(findService.getUser(email));
    }

    public void addBan(Long id){
        User user = findService.getById(id);
        user.setLocked(true);
        usersRepo.save(user);
    }

    public void deleteBan(Long id){
        User user = findService.getById(id);
        user.setLocked(false);
        usersRepo.save(user);
    }



}
