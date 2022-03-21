package com.rgr.storeApp.service.favorites.profile.buy;


import com.rgr.storeApp.dto.product.BuyResponse;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.exceptions.api.NotPrivilege;
import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.models.basket.Buy;
import com.rgr.storeApp.models.basket.BuyHistory;
import com.rgr.storeApp.models.delivery.AwaitingList;
import com.rgr.storeApp.models.delivery.Delivery;
import com.rgr.storeApp.models.product.Store;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.product.ProductInfo;
import com.rgr.storeApp.models.profile.Sales;
import com.rgr.storeApp.models.profile.UserProfile;
import com.rgr.storeApp.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuyService {

    private final BuyRepo buyRepo;
    private final ProductsRepo productsRepo;
    private final UserProfileRepo userProfileRepo;
    private final SalesRepo salesRepo;
    private final DeliveryRepo deliveryRepo;

    @Autowired
    public BuyService(BuyRepo buyRepo,
                      ProductsRepo productsRepo,
                      UserProfileRepo userProfileRepo,
                      SalesRepo salesRepo,
                      DeliveryRepo deliveryRepo) {
        this.buyRepo = buyRepo;
        this.productsRepo = productsRepo;
        this.userProfileRepo = userProfileRepo;
        this.salesRepo = salesRepo;
        this.deliveryRepo = deliveryRepo;
    }


    public BuyResponse addBuy(UserProfile userProfile){
        List<Product> products = userProfile.getBasket().getProducts();
        if (products.size() != 0) { // Переделать проверку
            Integer balance = userProfile.getBalance();
            Basket basket = userProfile.getBasket();
            BuyHistory buyHistory = basket.getUserProfile().getBuyHistory();
            if (products != null) {
                Integer sum = products
                        .stream()
                        .map(e -> e.getProductInfo().getPrice()).collect(Collectors.toList())
                        .stream()
                        .reduce(0, Integer::sum);
                if (sum <= balance) {

                    List<Product> productList = new ArrayList<>();
                    for (Product i : products) {
                        if (buyProducer(userProfile, i)) {
                            System.out.println("work");
                            userProfile.minusMoney(i.getProductInfo().getPrice());
                            productList.add(i);
                        }
                    }
                    productList.stream().forEach(e->basket.removeProduct(e));
                    Delivery delivery = acceptBuy(userProfile, productList, sum); // ПЕРЕСЧИТАТЬ СУММУ
                    userProfileRepo.save(userProfile);
                    return BuyResponse.build(delivery);
                } else {
                    throw new NotPrivilege("No balance");
                }
            }
        }else {
            throw new NotFound("Basket is empty");
        }
        return null;
    }

    private boolean buyProducer(UserProfile userProfile, Product product){
        Store store = product.getStore();
        UserProfile profile = store.getUser().getUserProfile();
        ProductInfo productInfo = product.getProductInfo();
        int avaible = productInfo.getNumber();
        if(avaible > 0){
            productInfo.setNumber(avaible--);
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
        return false;
    }


    private Delivery acceptBuy(UserProfile userProfile, List<Product> products, Integer sum){
        AwaitingList awaitingList = userProfile.getAwaitingList();
        Buy buy = new Buy(products, LocalDateTime.now());
        buy.setSum(sum);
        // send check to email
        Delivery delivery = new Delivery(LocalDateTime.now(), LocalDateTime.now().plus(Period.ofDays(15)));
        BuyHistory buyHistory = userProfile.getBuyHistory();
        buy.setBuyHistory(buyHistory);
        buy.setDelivery(delivery);
        buyRepo.save(buy);
        buyHistory.getBuys().add(buy);
        awaitingList.getDeliveries().add(delivery);
        delivery.setList(awaitingList);
        delivery.setBuy(buy);
        return deliveryRepo.save(delivery);
    }


}
