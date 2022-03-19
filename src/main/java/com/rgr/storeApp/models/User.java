package com.rgr.storeApp.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rgr.storeApp.models.product.Store;
import com.rgr.storeApp.models.profile.UserProfile;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Setter
@Getter
@Table(name = "users"
        ,uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User implements Serializable {

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

    @JsonIgnore
    private String email;

    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private boolean enabled; //ignore

    @JsonIgnore
    private boolean locked;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "app_users_roles",
                joinColumns = @JoinColumn(name = "users_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();


    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private UserProfile userProfile;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Store store;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public User(String email, String username, String password, boolean enabled, boolean locked, Set<Role> roles, UserProfile userProfile, Store store) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.locked = locked;
        this.roles = roles;
        this.userProfile = userProfile;
        this.store = store;
    }
}
