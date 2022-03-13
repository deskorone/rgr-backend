package com.rgr.storeApp.models.product;


import com.rgr.storeApp.models.User;
import lombok.Data;
import lombok.ToString;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String reviewText;


    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                '}';
    }
}
