package com.rgr.storeApp.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {


    private Integer price;

    private String description;

    private Integer number;

    private List<String> categories;

    private String materials;

    @Override
    public String toString() {
        return "ProductRequest{" +
                "price=" + price +
                ", description='" + description + '\'' +
                ", number=" + number +
                ", categories=" + categories +
                ", materials='" + materials + '\'' +
                '}';
    }
}
