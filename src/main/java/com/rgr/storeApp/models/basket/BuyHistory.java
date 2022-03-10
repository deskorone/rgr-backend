package com.rgr.storeApp.models.basket;


import com.rgr.storeApp.models.profile.UserProfile;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    UserProfile userProfile;


}
