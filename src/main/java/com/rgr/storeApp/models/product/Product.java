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

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;


    //TODO написать логику скидок

    public Product(String id_code, List<Review> reviews, List<Category> categories, ProductInfo productInfo, Producer producer) {
        this.id_code = id_code;
        this.reviews = reviews;
        this.categories = categories;
        this.productInfo = productInfo;
        this.producer = producer;
    }

    @ManyToMany()
    @JoinTable(name = "product_categories",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductInfo productInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producer_id")
    private Producer producer;

}
