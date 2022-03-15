package com.rgr.storeApp.models.basket;


import com.rgr.storeApp.models.product.Producer;
import com.rgr.storeApp.models.profile.Sales;
import com.rgr.storeApp.models.profile.UserProfile;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "sellHistory")
@ToString
public class SellHistory {


    public SellHistory(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "sellHistory")
    private List<Sales> sales;

    @OneToOne
    @JoinColumn(name = "producer_id", referencedColumnName = "id")
    Producer producer;

}
