package com.rgr.storeApp.service.delivery;


import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.delivery.AwaitingList;
import com.rgr.storeApp.models.delivery.Delivery;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.profile.UserProfile;
import com.rgr.storeApp.repo.DeliveryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private  final DeliveryRepo deliveryRepo;


    @Autowired
    public DeliveryService(DeliveryRepo deliveryRepo) {
        this.deliveryRepo = deliveryRepo;
    }





}
