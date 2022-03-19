package com.rgr.storeApp.dto.userProfile;


import com.rgr.storeApp.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGeneralProfileResponse {

    private Integer balance;
    private String username;
    private Integer inBasket;

    public static UserGeneralProfileResponse build(User user){
        return new UserGeneralProfileResponse(user.getUserProfile().getBalance(), user.getUsername(), user.getUserProfile().getBasket().getProducts().size());
    }
}
