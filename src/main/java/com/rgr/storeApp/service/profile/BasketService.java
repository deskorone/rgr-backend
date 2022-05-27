package com.rgr.storeApp.service.profile;


import com.rgr.storeApp.dto.userProfile.BasketDto;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.exceptions.api.NotPrivilege;
import com.rgr.storeApp.models.ERole;
import com.rgr.storeApp.models.Role;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.profile.UserProfile;
import com.rgr.storeApp.repo.BasketRepo;
import com.rgr.storeApp.repo.ProductsRepo;
import com.rgr.storeApp.repo.UsersRepo;
import com.rgr.storeApp.service.find.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {


    private final BasketRepo basketRepo;
    private final UsersRepo usersRepo;
    private final ProductsRepo productsRepo;
    private final FindService findService;

    @Autowired
    public BasketService(BasketRepo basketRepo, UsersRepo usersRepo, ProductsRepo productsRepo, FindService findService) {
        this.basketRepo = basketRepo;
        this.usersRepo = usersRepo;
        this.productsRepo = productsRepo;
        this.findService = findService;
    }


    public BasketDto addProductInBasket(Long id){
        User user = findService.getUser(findService.getEmailFromAuth());
        boolean isSalesman = false;
        UserProfile userProfile = user.getUserProfile();
        Product product = productsRepo.findById(id).orElseThrow(()->new NotFound("Product MOT found"));
        for (Role i : user.getRoles()){
            if(i.getRole().equals(ERole.ROLE_SALESMAN)){
                isSalesman = true;
            }
        }
        if (isSalesman){
            boolean error = user.getStore().getProducts()
                    .stream()
                    .anyMatch(e-> e.getId() == id);
            if (error){
                throw new NotPrivilege("Error it's you product");
            }
        }
        Basket basket = userProfile.getBasket();
        basket.getProducts().add(product);
        basketRepo.save(basket);
        return BasketDto.build(basket);
    }

    public BasketDto deleteProduct(Long id){
        UserProfile userProfile = findService.getUser(findService.getEmailFromAuth()).getUserProfile();
        Product product = productsRepo.findById(id).orElseThrow(()->new NotFound("Product MOT found"));
        Basket basket = userProfile.getBasket();
        basket.removeProduct(product);
        basketRepo.save(basket);
        return BasketDto.build(basket);
    }





}
