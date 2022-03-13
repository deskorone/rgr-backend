package com.rgr.storeApp.models.delivery;


import com.rgr.storeApp.models.basket.Buy;
import com.rgr.storeApp.models.product.Product;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@ToString
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private LocalDateTime created;

    private LocalDateTime arrival;

    @OneToOne
    @JoinColumn(name = "buy_id", nullable = false)
    private Buy buy;


    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", created=" + created +
                ", arrival=" + arrival +
                '}';
    }
}
