package com.rgr.storeApp.service.profile.buy;


import com.rgr.storeApp.dto.product.ProductLiteResponse;
import com.rgr.storeApp.dto.userProfile.DeliveryDto;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.exceptions.api.NotPrivilege;
import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.models.basket.Buy;
import com.rgr.storeApp.models.basket.BuyHistory;
import com.rgr.storeApp.models.delivery.Delivery;
import com.rgr.storeApp.models.product.Store;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.product.ProductInfo;
import com.rgr.storeApp.models.profile.Sales;
import com.rgr.storeApp.models.profile.UserProfile;
import com.rgr.storeApp.repo.*;
import com.rgr.storeApp.service.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Slf4j
@Service
public class BuyService {

    private final BuyRepo buyRepo;
    private final ProductsRepo productsRepo;
    private final UserProfileRepo userProfileRepo;
    private final SalesRepo salesRepo;
    private final DeliveryRepo deliveryRepo;
    private final EmailService emailService;


    @Autowired
    public BuyService(BuyRepo buyRepo,
                      ProductsRepo productsRepo,
                      UserProfileRepo userProfileRepo,
                      SalesRepo salesRepo,
                      DeliveryRepo deliveryRepo,
                      EmailService emailService) {
        this.buyRepo = buyRepo;
        this.productsRepo = productsRepo;
        this.userProfileRepo = userProfileRepo;
        this.salesRepo = salesRepo;
        this.deliveryRepo = deliveryRepo;
        this.emailService = emailService;
    }


    public DeliveryDto addBuy(UserProfile userProfile){
        List<Product> products = userProfile.getBasket().getProducts();
        if (products.size() != 0) {
            Integer balance = userProfile.getBalance();
            Basket basket = userProfile.getBasket();
                Integer sum = products
                        .stream()
                        .map(e -> e.getProductInfo().getPrice()).collect(Collectors.toList())
                        .stream()
                        .reduce(0, Integer::sum);
                if (sum <= balance) {

                    List<Product> productList = new ArrayList<>();
                    for (Product i : products) {
                        if (buyProducer(userProfile, i)) {
                            userProfile.minusMoney(i.getProductInfo().getPrice());
                            productList.add(i);
                        }
                    }
                    if(productList.size()==0){throw new NotFound("You basket empty");}
                    productList.forEach(basket::removeProduct);
                    Delivery delivery = acceptBuy(userProfile, productList,
                            productList
                            .stream()
                            .map(e -> e.getProductInfo().getPrice()).collect(Collectors.toList())
                            .stream()
                            .reduce(0, Integer::sum));
                    log.info(String.format("Succesfull buy user: %s", userProfile.getUser().getUsername()));
                    userProfile.getAwaitingList().getDeliveries().add(delivery);
                    try {
                        emailService.sendCheck(userProfile.getUser().getEmail(), products.stream().map(ProductLiteResponse::build).collect(Collectors.toList()));
                    }catch (Exception e){
                        throw new NotPrivilege(e.getMessage());
                    }
                    userProfileRepo.save(userProfile);
                    return DeliveryDto.build(delivery);
                } else {
                    throw new NotPrivilege("No balance");
                }
        }else {
            throw new NotFound("Basket is empty");
        }
    }

    private boolean buyProducer(UserProfile userProfile, Product product){

        if(!Objects.equals(userProfile.getUser().getStore().getId(), product.getStore().getId())) {
            Store store = product.getStore();
            UserProfile profile = store.getUser().getUserProfile();
            ProductInfo productInfo = product.getProductInfo();
            if (productInfo.getNumber() > 0) {
                productInfo.setNumber(productInfo.getNumber() - 1);
                profile.addMoney(productInfo.getPrice());
                Sales sales = new Sales(LocalDateTime.now(),
                        store.getSellHistory(),
                        product);
                sales.setBuyer(userProfile.getUser());
                salesRepo.save(sales);
                userProfileRepo.save(profile);
                productsRepo.save(product);
                return true;
            }
        }
        return false;
    }


    private Delivery acceptBuy(UserProfile userProfile, List<Product> products, Integer sum){
        BuyHistory buyHistory = userProfile.getBuyHistory();
        Buy buy = new Buy(products, LocalDateTime.now(), sum , buyHistory);
        Delivery delivery = new Delivery(LocalDateTime.now(), LocalDateTime.now().plusDays(10L));
        delivery.setBuy(buy);
        buy.setDelivery(delivery);
        buyRepo.save(buy);
        return deliveryRepo.save(delivery);

    }



}
