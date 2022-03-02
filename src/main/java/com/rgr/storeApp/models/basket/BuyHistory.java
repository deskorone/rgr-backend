package com.rgr.storeApp.models.basket;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "buy_history")
public class BuyHistory {

    public BuyHistory(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "buy_id")
    private List<Buy> buys;


}
