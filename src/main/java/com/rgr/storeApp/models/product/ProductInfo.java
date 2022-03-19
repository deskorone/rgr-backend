package com.rgr.storeApp.models.product;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rgr.storeApp.models.User;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "product_info")
public class ProductInfo {

    public ProductInfo(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    private ProductPhoto mainPhoto;

    //NOT NULL
    private Integer price;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductPhoto> productPhotos;

    public ProductInfo(ProductPhoto mainPhoto, Integer price, List<ProductPhoto> productPhotos, Integer number, String description, String materials) {
        this.mainPhoto = mainPhoto;
        this.price = price;
        this.productPhotos = productPhotos;
        this.number = number;
        this.description = description;
        this.materials = materials;
    }

    @JsonBackReference
    private Integer number;

    private Integer sale;

    public ProductInfo(ProductPhoto mainPhoto, Integer price, List<ProductPhoto> productPhotos, Integer number, Integer sale, String description, String materials) {
        this.mainPhoto = mainPhoto;
        this.price = price;
        this.productPhotos = productPhotos;
        this.number = number;
        this.sale = sale;
        this.description = description;
        this.materials = materials;
    }

    private String description;

    private String materials;

    public ProductInfo(String description, String materials) {
        this.description = description;
        this.materials = materials;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "price=" + price +
                '}';
    }
}
