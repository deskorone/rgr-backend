package com.rgr.storeApp.models.product;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.basket.SellHistory;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "producer")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
public class Producer implements Serializable {

    public Producer(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String town;
    private String address;

    @JsonBackReference
    @OneToMany(mappedBy = "producer", cascade = CascadeType.ALL)
    private List<Product> products;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "history_id")
    private SellHistory sellHistory;

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", town='" + town + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

}
