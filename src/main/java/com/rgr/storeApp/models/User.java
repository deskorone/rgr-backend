package com.rgr.storeApp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.models.product.Producer;
import com.rgr.storeApp.models.profile.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Table(name = "users"
        , uniqueConstraints = {@UniqueConstraint(columnNames = "email")}) // TODO тут ошибка может быть
public class User {

    public User() {}

    public User(String email, String username, String password, String country, boolean enabled, boolean locked) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.country = country;
        this.enabled = enabled;
        this.locked = locked;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String username;
    private String password;
    private String country;
    private boolean enabled;
    private boolean locked;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();

    @OneToOne
    @JsonBackReference
    private UserProfile userProfile;

    @OneToOne
    private Producer producer;


}
