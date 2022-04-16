package com.rgr.storeApp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;;


import javax.validation.constraints.*;
import java.util.Set;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {



    @Email(message = "Email not valide")
    @NotNull
    private String email;

    @NotEmpty(message = "Username is empty")
    @NotNull(message = "Username not null")
    private String username;

    @NotEmpty
    @NotNull
    @Size(min = 6, message = "Min password lenght 6")
    private String password;

    @NotEmpty
    private Set<String> roles;

}
