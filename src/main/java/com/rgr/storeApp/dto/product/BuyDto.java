package com.rgr.storeApp.dto.product;


import com.rgr.storeApp.models.basket.Buy;
import com.rgr.storeApp.models.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyDto {

    private Long id;
    private List<ProductLiteResponse> products;
    private Integer sum;

    public static BuyDto build(Buy buy){
        List<ProductLiteResponse> productLiteResponses =
                buy.getProducts()
                        .stream()
                        .map(e-> ProductLiteResponse.build(e, null))
                        .collect(Collectors.toList());
        return new BuyDto(buy.getId(), productLiteResponses, buy.getSum());
    }


}
