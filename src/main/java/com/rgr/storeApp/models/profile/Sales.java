package com.rgr.storeApp.models.profile;


import com.rgr.storeApp.models.basket.SellHistory;
import com.rgr.storeApp.models.product.Product;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "sales")
public class Sales {

    public Sales(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Sales(LocalDateTime date, SellHistory sellHistory, Product product) {
        this.date = date;
        this.sellHistory = sellHistory;
        this.product = product;
    }

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "history_id")
    private SellHistory sellHistory;


    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public String toString() {
        return "Sales{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}
