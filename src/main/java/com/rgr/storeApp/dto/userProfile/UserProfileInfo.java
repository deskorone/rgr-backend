package com.rgr.storeApp.dto.userProfile;


import com.rgr.storeApp.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserProfileInfo {

    private Long id;
    private String username;
    private String lastname;
    private String town;
    private Integer index;
    private String email;
    private List<String> roles;


    public static UserProfileInfo build(User user){

        return new UserProfileInfo(
                user.getId(),
                user.getUsername(),
                user.getLastname(),
                user.getUserProfile().getTown(),
                user.getUserProfile().getIndex(),
                user.getEmail(),
                user.getRoles().stream().map(el -> el.getRole().toString()).collect(Collectors.toList()));
    }

}
