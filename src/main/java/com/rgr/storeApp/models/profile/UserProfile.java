package com.rgr.storeApp.models.profile;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.models.basket.BuyHistory;
import com.rgr.storeApp.models.basket.SellHistory;
import com.rgr.storeApp.models.delivery.AwaitingList;
import com.rgr.storeApp.models.product.Favorites;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@Table(name = "user_profile")
public class UserProfile implements Serializable {

    public UserProfile(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "favorites_id")
    private Favorites favorites;

    private String town;

    private Integer index;

    private Integer balance;

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }


    public void addMoney(int a ){this.balance +=a;}

    public void minusMoney(int a){this.balance -= a;}
}
