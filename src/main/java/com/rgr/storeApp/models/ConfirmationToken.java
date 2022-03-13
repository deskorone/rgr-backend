package com.rgr.storeApp.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime expired;
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false,
            name = "user_id"
    )
    private User user;


    public ConfirmationToken(String token, LocalDateTime created, LocalDateTime expired, User user) {
        this.token = token;
        this.created = created;
        this.expired = expired;
        this.user = user;
    }

}

