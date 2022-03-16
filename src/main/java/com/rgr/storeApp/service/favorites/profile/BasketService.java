package com.rgr.storeApp.service.favorites.profile;


import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.profile.UserProfile;
import com.rgr.storeApp.repo.BasketRepo;
import com.rgr.storeApp.repo.ProductsRepo;
import com.rgr.storeApp.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {


    private final BasketRepo basketRepo;
    private final UsersRepo usersRepo;
    private final ProductsRepo productsRepo;

    @Autowired
    public BasketService(BasketRepo basketRepo, UsersRepo usersRepo, ProductsRepo productsRepo) {
        this.basketRepo = basketRepo;
        this.usersRepo = usersRepo;
        this.productsRepo = productsRepo;
    }


    public Basket addProductInBasket(String email, Long id){
        UserProfile userProfile = usersRepo
                .findByEmail(email)
                .orElseThrow(()-> new NotFound("NOT FOUND user"))
                .getUserProfile();
        Product product = productsRepo.findById(id).orElseThrow(()->new NotFound("Product MOT found"));

        Basket basket = userProfile.getBasket();
        basket.getProducts().add(product);
        basketRepo.save(basket);
        return basket;
    }

    public Basket deleteProduct(String email, Long id){
        UserProfile userProfile = usersRepo
                .findByEmail(email)
                .orElseThrow(()-> new NotFound("NOT FOUND user"))
                .getUserProfile();
        Product product = productsRepo.findById(id).orElseThrow(()->new NotFound("Product MOT found"));

        Basket basket = userProfile.getBasket();
        basket.removeProduct(product);
        basketRepo.save(basket);
        return basket;

    }





}
