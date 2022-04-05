package com.rgr.storeApp.dto.userProfile;


import com.rgr.storeApp.models.basket.BuyHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyHistoryDto {

    private Long id;
    private List<BuyDto> buys;

    public static BuyHistoryDto build(BuyHistory buyHistory){

        return new BuyHistoryDto(buyHistory.getId(),
                buyHistory.getBuys()
                        .stream()
                        .map(BuyDto::build)
                        .collect(Collectors.toList())
                );

    }

}
