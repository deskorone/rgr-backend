package com.rgr.storeApp.service.buy;


import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.models.basket.Buy;
import com.rgr.storeApp.models.product.Producer;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.product.ProductInfo;
import com.rgr.storeApp.models.profile.Sales;
import com.rgr.storeApp.models.profile.UserProfile;
import com.rgr.storeApp.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    public BuyService(BuyRepo buyRepo, BasketRepo basketRepo, ProductsRepo productsRepo, ProducerRepo producerRepo, UserProfileRepo userProfileRepo, SalesHistoryRepo salesHistoryRepo) {
        this.buyRepo = buyRepo;
        this.basketRepo = basketRepo;
        this.productsRepo = productsRepo;
        this.producerRepo = producerRepo;
        this.userProfileRepo = userProfileRepo;
        this.salesHistoryRepo = salesHistoryRepo;
    }


    public void addBuy(UserProfile userProfile){
        List<Product> products = userProfile.getBasket().getProducts();
        Integer balance = userProfile.getBalance();
        Basket basket = userProfile.getBasket();
        if(products != null){
            Integer sum = products
                    .stream()
                    .map(e-> e.getProductInfo().getPrice()).collect(Collectors.toList())
                    .stream()
                    .reduce(0, Integer::sum);
            if(sum <= balance){
                for (Product i : products){
                    if(buyProducer(i)){
                        userProfile.minusBalance(i.getProductInfo().getPrice());
                        basket.removeProduct(i); //save in history; and deliveres ...
                    }
                }
            }else {
                //throw
            }
        }

    }

    public boolean buyProducer(Product product){
        Producer producer = product.getProducer();
        UserProfile profile = producer.getUser().getUserProfile();
        ProductInfo productInfo = product.getProductInfo();
        int avaible = productInfo.getNumber();

        if(avaible > 0){
            profile.addBalance(productInfo.getPrice());
            productInfo.setNumber(avaible--);
            Sales sales = new Sales(LocalDateTime.now(),
                    producer.getSellHistory(),
                    product);

            producerRepo.save(producer);
            userProfileRepo.save(profile);
            productsRepo.save(product);
            return true;
        }
        return false;
    }

    public Buy buy(Product product){
        return null;
    }


}
