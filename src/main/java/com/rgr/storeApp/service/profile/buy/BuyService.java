package com.rgr.storeApp.service.profile.buy;


import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.models.basket.Buy;
import com.rgr.storeApp.models.basket.BuyHistory;
import com.rgr.storeApp.models.basket.SellHistory;
import com.rgr.storeApp.models.product.Producer;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.product.ProductInfo;
import com.rgr.storeApp.models.profile.Sales;
import com.rgr.storeApp.models.profile.UserProfile;
import com.rgr.storeApp.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuyService {

    private final BuyRepo buyRepo;
    private final BasketRepo basketRepo;
    private final ProductsRepo productsRepo;
    private final ProducerRepo producerRepo;
    private final UserProfileRepo userProfileRepo;
    private final SalesHistoryRepo salesHistoryRepo;
    private final BuyHistoryRepo buyHistoryRepo;
    private final ProductInfoRepo productInfoRepo;
    private final SalesRepo salesRepo;

    @Autowired
    public BuyService(BuyRepo buyRepo, BasketRepo basketRepo, ProductsRepo productsRepo, ProducerRepo producerRepo, UserProfileRepo userProfileRepo, SalesHistoryRepo salesHistoryRepo, BuyHistoryRepo buyHistoryRepo, ProductInfoRepo productInfoRepo, SalesRepo salesRepo) {
        this.buyRepo = buyRepo;
        this.basketRepo = basketRepo;
        this.productsRepo = productsRepo;
        this.producerRepo = producerRepo;
        this.userProfileRepo = userProfileRepo;
        this.salesHistoryRepo = salesHistoryRepo;
        this.buyHistoryRepo = buyHistoryRepo;
        this.productInfoRepo = productInfoRepo;
        this.salesRepo = salesRepo;
    }


    public void addBuy(UserProfile userProfile){
        List<Product> products = userProfile.getBasket().getProducts();
        if (products.size() != 0) {
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
                        if (buyProducer(i)) {
                            System.out.println("work");
                            userProfile.minusMoney(i.getProductInfo().getPrice());
                            productList.add(i);
                        }
                    }
 //                   productList.stream().forEach(e->basket.removeProduct(e));
                    Buy buy = new Buy(productList, LocalDateTime.now());
                    buy.setBuyHistory(buyHistory);
                    userProfileRepo.save(userProfile);
                } else {

                }
            }
        }
    }

    public boolean buyProducer(Product product){
        Producer producer = product.getProducer();
        UserProfile profile = producer.getUser().getUserProfile();
        ProductInfo productInfo = product.getProductInfo();
        int avaible = productInfo.getNumber();

        if(avaible > 0){
            productInfo.setNumber(avaible - 1);
            profile.addMoney(productInfo.getPrice());
            Sales sales = new Sales(LocalDateTime.now(),
                    producer.getSellHistory(),
                    product);
            salesRepo.save(sales);
            userProfileRepo.save(profile);
            productsRepo.save(product);
            return true;
        }
        return false;
    }


    public void acceptBuy(UserProfile userProfile, List<Product> products){


    }

    //метод поккпки где созлается доаствка и история



}
