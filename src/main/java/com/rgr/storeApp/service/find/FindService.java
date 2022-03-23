package com.rgr.storeApp.service.find;

import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.repo.ProductsRepo;
import com.rgr.storeApp.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class FindService {

    private final UsersRepo usersRepo;
    private final ProductsRepo productsRepo;


    @Autowired
    public FindService(UsersRepo usersRepo, ProductsRepo productsRepo) {
        this.usersRepo = usersRepo;
        this.productsRepo = productsRepo;
    }


    public User getUser(String email){
        return usersRepo.findByEmail(email).orElseThrow(()->new NotFound("Use not found"));
    }

    public String getEmailFromAuth(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    public Product findProduct(Long id){
        return productsRepo.findById(id).orElseThrow(()-> new NotFound("Product not found"));
    }

}
