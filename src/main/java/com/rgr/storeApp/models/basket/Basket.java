package com.rgr.storeApp.models.basket;


import com.rgr.storeApp.models.product.Product;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "basket")
public class Basket {

    public Basket(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favorites",
                joinColumns = @JoinColumn(name = "basket_id"),
                inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;




}
