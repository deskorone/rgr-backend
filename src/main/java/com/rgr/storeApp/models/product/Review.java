package com.rgr.storeApp.models.product;


import com.rgr.storeApp.models.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "reviews")
public class Review {

    public Review(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<User> users;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


}
