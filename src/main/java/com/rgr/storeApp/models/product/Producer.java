package com.rgr.storeApp.models.product;


import com.rgr.storeApp.models.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "producer")
public class Producer {

    public Producer(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String town;
    private String address;

    @OneToMany(mappedBy = "producer")
    private List<Product> products;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Producer(String country, String town, String address, List<Product> products) {
        this.country = country;
        this.town = town;
        this.address = address;
        this.products = products;
    }
}
