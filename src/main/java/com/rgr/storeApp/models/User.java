package com.rgr.storeApp.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rgr.storeApp.models.product.Store;
import com.rgr.storeApp.models.profile.UserProfile;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Setter
@Getter
@Validated
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



    @Email(message = "Email not valide")
    @NotEmpty(message = "Email is empty")
    private String email;

    @NotEmpty(message = "Username empty")
    private String username;

    private String lastname;

    @JsonIgnore
    @NotEmpty(message = "Password is empty")
    private String password;

    @JsonIgnore
    private boolean enabled;

    @JsonIgnore
    private boolean locked;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "app_users_roles",
                joinColumns = @JoinColumn(name = "users_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();


    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserProfile userProfile;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
