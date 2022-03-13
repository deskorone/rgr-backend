package com.rgr.storeApp.models.product;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@Table(name = "photo")
public class ProductPhoto {

    public ProductPhoto(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductPhoto(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ProductPhoto{" +
                "id=" + id +
                ", path='" + path + '\'' +
                '}';
    }
}
