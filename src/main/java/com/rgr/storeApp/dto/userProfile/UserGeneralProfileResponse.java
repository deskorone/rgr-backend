package com.rgr.storeApp.dto.userProfile;


import com.rgr.storeApp.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGeneralProfileResponse {


    private Long id;
    private Integer balance;
    private String username;
    private Integer inBasket;

    public static UserGeneralProfileResponse build(User user){
        return new UserGeneralProfileResponse(user.getId(), user.getUserProfile().getBalance(), user.getUsername(), user.getUserProfile().getBasket().getProducts().size());
    }
}
