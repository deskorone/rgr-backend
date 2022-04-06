package com.rgr.storeApp.models.delivery;


import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Delivery> deliveries;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    UserProfile userProfile;

    @Override
    public String toString() {
        return "AwaitingList{" +
                "id=" + id +
                '}';
    }

    public void deleteDelivery(Delivery delivery){
        deliveries.remove(delivery);
    }

}
