package com.rgr.storeApp.dto.userProfile;


import com.rgr.storeApp.models.delivery.AwaitingList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwaitingListDto {

    private Long id;
    private List<DeliveryDto> deliveries;

    public static AwaitingListDto build(AwaitingList awaitingList){
        List<DeliveryDto> list = awaitingList.getDeliveries()
                .stream()
                .map(DeliveryDto::build)
                .collect(Collectors.toList());
        return new AwaitingListDto(awaitingList.getId(), list);
    }
}
