package com.rgr.storeApp.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private String email;
    private String username;
    private String password;
    private Set<String> roles;

}
