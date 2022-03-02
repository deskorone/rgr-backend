package com.rgr.storeApp.models.product;


import com.rgr.storeApp.models.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "product_info")
public class ProductInfo {

    public ProductInfo(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "salesman_id")
    private User salesman;
}
