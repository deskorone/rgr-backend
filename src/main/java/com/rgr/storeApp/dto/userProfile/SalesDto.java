package com.rgr.storeApp.dto.userProfile;

import com.rgr.storeApp.dto.product.ProductLiteResponse;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.profile.Sales;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalesDto {

    private LocalDateTime dateTime;
    private ProductLiteResponse product;
    private UserProfileInfo buyerInfo;

    public static SalesDto build(Sales sales){
        return new SalesDto(
                sales.getDate(),
                ProductLiteResponse.build(sales.getProduct(), null),
                UserProfileInfo.build(sales.getBuyer())
        );
    }

}
