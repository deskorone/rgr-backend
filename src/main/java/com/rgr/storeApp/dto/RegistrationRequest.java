package com.rgr.storeApp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;;


import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @Min(3)
    @Email(message = "err")
    @NotEmpty
    private String email;

    @NotNull
    private String username;

    private String password;
    private Set<String> roles;

}
