package com.rgr.storeApp.dto.userProfile;


import lombok.Data;

@Data
public class UserInfoRequest {
    private String username;
    private String lastname;
    private Integer index;
    private String town;
}
