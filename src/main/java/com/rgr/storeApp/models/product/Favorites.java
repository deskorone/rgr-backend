package com.rgr.storeApp.models.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rgr.storeApp.models.profile.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "favorites")
public class Favorites implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "user_profile_id",  referencedColumnName = "id")
    private UserProfile userProfile;


    @ManyToMany(cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @JoinTable(name = "favorites_products",
            joinColumns = @JoinColumn(name = "favorites_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    public void remove(Product product){
        this.products.remove(product);
        product.getFavorites().remove(this);
    }


    public Favorites() {
    }
}
