package com.rgr.storeApp.models.profile;


import com.rgr.storeApp.models.product.Product;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "sales")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;


    @ManyToMany
    @JoinTable(name = "sales_products",
    joinColumns = @JoinColumn(name = "sales_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

}
