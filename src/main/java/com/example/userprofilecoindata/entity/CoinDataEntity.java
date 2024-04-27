package com.example.userprofilecoindata.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "coin_data")
public class CoinDataEntity {
    @Id
    @SequenceGenerator(name = "seq_coin_data_id", sequenceName = "seq_coin_data_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_coin_data_id")
    private Long id;

    private Long userId;

    private String request;

    @Column(length = 65535)
    private String response;

    @CreationTimestamp
    @Column(name = "created_ts", updatable = false)
    private LocalDateTime createdTs;
}

