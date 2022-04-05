package com.rgr.storeApp.models.delivery;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rgr.storeApp.models.basket.Buy;
import com.rgr.storeApp.models.product.Product;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Delivery {

    public Delivery(LocalDateTime created, LocalDateTime arrival) {
        this.created = created;
        this.arrival = arrival;
    }

    public Delivery() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private LocalDateTime created;

    private LocalDateTime arrival;


    @OneToOne
    @JoinColumn(name = "buy_id", nullable = false)
    private Buy buy;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "awaiblelist_id")
    private AwaitingList list;


    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", created=" + created +
                ", arrival=" + arrival +
                '}';
    }
}
