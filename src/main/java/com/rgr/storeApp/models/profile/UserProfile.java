package com.rgr.storeApp.models.profile;


import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.models.basket.BuyHistory;
import com.rgr.storeApp.models.delivery.AwaitingList;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @OneToOne
    @JoinColumn(name = "history_id")
    private BuyHistory buyHistory;

    @OneToOne
    @JoinColumn(name = "awaitinglist_id")
    private AwaitingList awaitingList;

    private Float balance;

}
