package com.rgr.storeApp.models;


import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.models.profile.UserProfile;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users") // TODO тут ошибка может быть
public class User {

    public User() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String username;
    private String password;
    private String Country;
    private boolean enabled;
    private boolean locked;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();

    @OneToOne
    private UserProfile userProfile;


}
