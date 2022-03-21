package com.rgr.storeApp.service.favorites.profile.delivery;


import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.delivery.AwaitingList;
import com.rgr.storeApp.repo.UsersRepo;
import com.rgr.storeApp.service.find.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private final UsersRepo usersRepo;
    private final FindService findService;

    @Autowired
    public DeliveryService(UsersRepo usersRepo, FindService findService) {
        this.usersRepo = usersRepo;
        this.findService = findService;
    }


    public AwaitingList getDeliveries(String email){
        AwaitingList awaitingList = findService.getUser(findService.getEmailFromAuth())
                .getUserProfile()
                .getAwaitingList();
        return  awaitingList;
    }


    //TODO delivery refresh?


}
