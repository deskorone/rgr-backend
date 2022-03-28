package com.rgr.storeApp.dto.userProfile;


import com.rgr.storeApp.dto.product.ProductLiteResponse;
import com.rgr.storeApp.models.basket.Basket;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class BasketDto {

    private Long id;
    private List<ProductLiteResponse> products;

    public static BasketDto build(Basket basket){

        List<ProductLiteResponse>  responses = basket.getProducts()
                .stream()
                .map(e ->{
                    return ProductLiteResponse.build(e, null);
                }
                ).collect(Collectors.toList());

        return new BasketDto(basket.getId(), responses);

    }

}
