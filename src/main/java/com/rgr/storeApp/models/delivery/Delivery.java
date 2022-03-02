package com.rgr.storeApp.models.delivery;


import com.rgr.storeApp.models.product.Product;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private LocalDateTime created;

    private LocalDateTime arrival;

    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;



}
