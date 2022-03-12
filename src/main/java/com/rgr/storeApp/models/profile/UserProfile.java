package com.rgr.storeApp.models.profile;


import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.models.basket.BuyHistory;
import com.rgr.storeApp.models.basket.SellHistory;
import com.rgr.storeApp.models.delivery.AwaitingList;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@Table(name = "user_profile")
public class UserProfile {

    public UserProfile(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "history_id")
    private BuyHistory buyHistory;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "awaitinglist_id")
    private AwaitingList awaitingList;

    private Integer balance;



    public UserProfile(Basket basket, BuyHistory buyHistory, AwaitingList awaitingList, Integer balance) {
        this.basket = basket;
        this.buyHistory = buyHistory;
        this.awaitingList = awaitingList;
        this.balance = balance;
    }


    public int addBalance(int a){
        return this.balance += a;
    }

    public int minusBalance(int a){
        return this.balance -= a;
    }

}
