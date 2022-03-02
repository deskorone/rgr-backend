package com.rgr.storeApp.models.delivery;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "awaitings")
public class AwaitingList {

    public AwaitingList(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: мейби проверять при каждом запросе недействительные и удалять их?

    @OneToMany(fetch = FetchType.EAGER)
    private List<Delivery> deliveries;

}
