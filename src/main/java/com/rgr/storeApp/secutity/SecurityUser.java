package com.rgr.storeApp.secutity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rgr.storeApp.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class SecurityUser implements UserDetails {

    private Long id;
    private String email;

    public SecurityUser(Long id,
                        String email,
                        String password,
                        Boolean enabled,
                        Boolean locked,
                        Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.locked = locked;
        this.authorities = authorities;
    }

    @JsonIgnore
    private String password;

    public SecurityUser() {

    }
    private Boolean enabled;


    public Long getId() {
        return id;
    }
    private Boolean locked;

    private Collection<? extends GrantedAuthority> authorities;


    public static SecurityUser createSecurityUser(User user){
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(r ->  new SimpleGrantedAuthority(r.getRole().name()))
                .collect(Collectors.toList());

        return new SecurityUser(user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                user.isLocked(),
                authorities);
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
