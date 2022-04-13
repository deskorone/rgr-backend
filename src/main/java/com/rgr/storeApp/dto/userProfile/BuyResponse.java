package com.rgr.storeApp.dto.userProfile;


import com.rgr.storeApp.models.delivery.Delivery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

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
