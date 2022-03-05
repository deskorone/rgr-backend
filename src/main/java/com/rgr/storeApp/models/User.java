package com.rgr.storeApp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rgr.storeApp.models.product.Producer;
import com.rgr.storeApp.models.profile.UserProfile;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Table(name = "users"
        ,uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {

    public User() {}

    public User(String email, String username, String password, boolean enabled, boolean locked) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.locked = locked;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String username;
    private String password;
    private boolean enabled;
    private boolean locked;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private UserProfile userProfile;

    @OneToOne(cascade = CascadeType.ALL)
    private Producer producer;


}
