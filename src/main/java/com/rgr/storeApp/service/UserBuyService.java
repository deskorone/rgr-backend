package com.rgr.storeApp.service;


import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.repo.BasketRepo;
import com.rgr.storeApp.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBuyService {

    private final UsersRepo usersRepo;
    private final BasketRepo basketRepo;


    @Autowired
    public UserBuyService(UsersRepo usersRepo, BasketRepo basketRepo) {
        this.usersRepo = usersRepo;
        this.basketRepo = basketRepo;
    }


    public Basket getBasket(String email){
        User user = usersRepo.findByEmail(email).orElseThrow(()->new NotFound("User not found"));
        return null;
    }



    public Basket putInBasket(String email, Long productId){

        return null;
    }

}
