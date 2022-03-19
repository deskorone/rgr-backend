package com.rgr.storeApp.dto.product;


import com.rgr.storeApp.models.delivery.Delivery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyResponse {

    LocalDateTime time;
    LocalDateTime arrived;


    public static BuyResponse build(Delivery delivery){
        return new BuyResponse(delivery.getCreated(), delivery.getArrival());
    }

}
