package com.rgr.storeApp.models.basket;


import com.rgr.storeApp.models.product.Producer;
import com.rgr.storeApp.models.profile.Sales;
import com.rgr.storeApp.models.profile.UserProfile;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "sellHistory")
public class SellHistory {


    public SellHistory(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Sales> sales;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producer_id", referencedColumnName = "id")
    Producer producer;

}
