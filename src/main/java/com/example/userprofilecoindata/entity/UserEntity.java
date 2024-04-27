package com.example.userprofilecoindata.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "user")
public class UserEntity {
    @Id
    @SequenceGenerator(name = "seq_user_id", sequenceName = "seq_user_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_id")
    private Long id;

    @Column(name = "username", unique = true)
    private String userName;

    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    private String mobile;
}
