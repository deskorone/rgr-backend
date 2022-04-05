package com.rgr.storeApp.models.basket;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.models.profile.UserProfile;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "basket")
public class Basket  {

    public Basket(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade =  {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    }, targetEntity = Product.class)
    @JoinTable(name = "product_in_busket",
                joinColumns = @JoinColumn(name = "basket_id"),
                inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    UserProfile userProfile;



    public void removeProduct(Product product){
        products.remove(product);

    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                '}';
    }
}
