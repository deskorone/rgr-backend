package com.rgr.storeApp.models.product;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rgr.storeApp.models.User;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "reviews")
public class Review {


    public Review(String reviewText, Integer rating) {
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public Review(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String reviewText;

    private Integer rating;


    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                '}';
    }


}
