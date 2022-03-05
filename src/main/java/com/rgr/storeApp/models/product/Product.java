package com.rgr.storeApp.models.product;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "product")
public class Product {

    public Product(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String id_code;

    @OneToOne
    private ProductPhoto mainPhoto;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "photo_id")
    private List<ProductPhoto> list;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @ManyToMany()
    @JoinTable(name = "product_categories",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @ManyToOne
    private Producer producer;

}
