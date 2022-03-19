package com.rgr.storeApp.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
public class RefreshToken {

    public RefreshToken(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;
    private LocalDateTime created;

    public RefreshToken(String token, LocalDateTime created, LocalDateTime expired) {
        this.token = token;
        this.created = created;
        this.expired = expired;
    }

    @Column(nullable = false)
    private LocalDateTime expired;

    private LocalDateTime refreshed;

    @ManyToOne
    @JoinColumn(nullable = false,
            name = "user_id"
    )
    private User user;

}
