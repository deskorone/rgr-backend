package com.rgr.storeApp.service.favorites.profile.delivery;


import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.delivery.AwaitingList;
import com.rgr.storeApp.models.delivery.Delivery;
import com.rgr.storeApp.repo.DeliveryRepo;
import com.rgr.storeApp.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {

    private final UsersRepo usersRepo;

    @Autowired
    public DeliveryService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }


    public AwaitingList getDeliveries(String email){
        AwaitingList awaitingList = usersRepo.findByEmail(email)
                .orElseThrow(()->new NotFound("User not found"))
                .getUserProfile().getAwaitingList();
        return  awaitingList;
    }


}
