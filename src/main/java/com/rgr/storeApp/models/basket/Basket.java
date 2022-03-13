package com.rgr.storeApp.models.basket;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.profile.UserProfile;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "basket")
public class Basket {

    public Basket(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favorites",
                joinColumns = @JoinColumn(name = "basket_id"),
                inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    UserProfile userProfile;

    public void removeProduct(Product product){
        this.products.remove(product);
        product.getBaskets().remove(this);

    }



}
