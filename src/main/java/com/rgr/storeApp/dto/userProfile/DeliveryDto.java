package com.rgr.storeApp.dto.userProfile;


import com.rgr.storeApp.models.delivery.Delivery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime arrived;
    private BuyDto buyDto;

    public static DeliveryDto build(Delivery delivery){
        return new DeliveryDto(delivery.getId(),
                delivery.getCreated(),
                delivery.getArrival(),
                BuyDto.build(delivery.getBuy()));
    }

}
