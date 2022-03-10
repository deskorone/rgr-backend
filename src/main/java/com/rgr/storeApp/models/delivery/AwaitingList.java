package com.rgr.storeApp.models.delivery;


import com.rgr.storeApp.models.profile.UserProfile;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "awaitings")
public class AwaitingList {

    public AwaitingList(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: мейби проверять при каждом запросе недействительные и удалять их?

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Delivery> deliveries;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    UserProfile userProfile;

}
