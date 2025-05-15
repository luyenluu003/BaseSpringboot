package com.example.demo.feature.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1231239774026L;

    @Id
    @Column
    private String userId;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    @Column
    private String baseOn; // email hoặc phone

    @Column
    private String password;

    @Column
    private String userName;

    @Column
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date tokenExpired;

    @Column
    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
