package com.rgr.storeApp.models.product;


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
    String country;
    String town;
    String address;

    @OneToMany(mappedBy = "producer")
    List<Product> products;
}
