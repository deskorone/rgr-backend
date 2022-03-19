package com.rgr.storeApp.models.basket;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rgr.storeApp.models.delivery.Delivery;
import com.rgr.storeApp.models.product.Product;
import lombok.Data;
import lombok.ToString;

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


    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(name = "buy_product",
    joinColumns = @JoinColumn(name = "buy_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    private LocalDateTime dateBuy;

    private Integer sum;


    public Buy(List<Product> products, LocalDateTime dateBuy) {
        this.products = products;
        this.dateBuy = dateBuy;
    }

    @Override
    public String toString() {
        return "Buy{" +
                "id=" + id +
                ", dateBuy=" + dateBuy +
                '}';
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "history_id")
    private BuyHistory buyHistory;

    @JsonIgnore
    @OneToOne(mappedBy = "buy", cascade = CascadeType.REMOVE)
    private Delivery delivery;

}
