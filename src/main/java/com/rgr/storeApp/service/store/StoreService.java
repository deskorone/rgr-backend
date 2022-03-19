package com.rgr.storeApp.service.store;


import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.product.Store;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    public boolean checkProduct(User user, Product product){
        return user.getEmail() == product.getStore().getUser().getEmail();
    }

}
