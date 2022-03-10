package com.rgr.storeApp.models.product;


import com.rgr.storeApp.models.User;
import lombok.Data;

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

    @OneToOne(cascade = CascadeType.ALL)
    private ProductPhoto mainPhoto;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductPhoto> productPhotos;

    public ProductInfo(ProductPhoto mainPhoto, List<ProductPhoto> productPhotos, String description, String materials) {
        this.mainPhoto = mainPhoto;
        this.productPhotos = productPhotos;
        this.description = description;
        this.materials = materials;
    }

    private String description;

    private String materials;

    public ProductInfo(String description, String materials) {
        this.description = description;
        this.materials = materials;
    }
}
