package com.rgr.storeApp.models.basket;


import com.rgr.storeApp.models.product.Store;
import com.rgr.storeApp.models.profile.Sales;
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "history_id")
    private List<Sales> sales;

    @OneToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    Store store;

}
