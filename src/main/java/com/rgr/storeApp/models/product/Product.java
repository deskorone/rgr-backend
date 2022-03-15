package com.rgr.storeApp.models.product;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.models.basket.Buy;
import com.rgr.storeApp.models.profile.Sales;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "product")
public class Product implements Serializable {

    public Product(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String id_code;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    public Product(String id_code, List<Review> reviews, List<Category> categories, ProductInfo productInfo, Producer producer) {
        this.id_code = id_code;
        this.reviews = reviews;
        this.categories = categories;
        this.productInfo = productInfo;
        this.producer = producer;
    }

    @ManyToMany(cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @JoinTable(name = "product_categories",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductInfo productInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_id")
    private Producer producer;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(name = "product_in_busket",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "basket_id"))
    private List<Basket> baskets;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(name = "buy_product",
            inverseJoinColumns = @JoinColumn(name = "buy_id"),
            joinColumns = @JoinColumn(name = "product_id"))
    private List<Buy> buys;



    @JsonBackReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Sales> sales;


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", id_code='" + id_code + '\'' +
                '}';
    }
}
