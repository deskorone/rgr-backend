package com.rgr.storeApp.dto.userProfile;


import com.rgr.storeApp.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileInfo {

    private String username;
    private String lastname;
    private String town;
    private Integer index;
    private String email;


    public static UserProfileInfo build(User user){
        return new UserProfileInfo(user.getUsername(),
                user.getLastname(),
                user.getUserProfile().getTown(),
                user.getUserProfile().getIndex(),
                user.getEmail());
    }

}
