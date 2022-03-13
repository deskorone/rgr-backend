package com.rgr.storeApp.models.basket;


import com.rgr.storeApp.models.product.Product;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "buys")
public class Buy {

    public Buy(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "buy_profuct",
    joinColumns = @JoinColumn(name = "buy_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    private LocalDateTime dateBuy;

    public Buy(List<Product> products, LocalDateTime dateBuy) {
        this.products = products;
        this.dateBuy = dateBuy;
    }
}
