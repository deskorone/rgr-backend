package com.rgr.storeApp.models.product;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "photo")
public class ProductPhoto {

    public ProductPhoto(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private byte[] photo;
}
