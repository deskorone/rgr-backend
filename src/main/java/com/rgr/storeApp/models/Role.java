package com.rgr.storeApp.models;


import lombok.Data;
import org.yaml.snakeyaml.events.Event;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
public class Role {

    public Role(ERole eRole){
        this.role = eRole;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole role;

    public Role(){}

}
