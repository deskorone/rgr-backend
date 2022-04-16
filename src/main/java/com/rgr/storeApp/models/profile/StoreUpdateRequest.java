package com.rgr.storeApp.models.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;




public class StoreUpdateRequest {


    @NotNull
    @Size(max = 2)
    private String country;

    @NotEmpty
    @Size(min = 2, message = "user name should have at least 2 characters")
    private String town;

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public String getTown() {
        return town;
    }

    public String getAddress() {
        return address;
    }

    public StoreUpdateRequest() {
    }

    public StoreUpdateRequest(String country, String town, String address) {
        this.country = country;
        this.town = town;
        this.address = address;
    }

    @NotEmpty
    @Size(min = 2, message = "user name should have at least 2 characters")
    private String address;


}
