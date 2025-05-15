package com.example.demo.feature.product.model;

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
@Table(name = "products")
public class Product implements Serializable {
    private static final long serialVersionUID = 1231239774021L;

    @Id
    @Column
    private String productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category; // Nam/Nữ

    @Column(nullable = false)
    private String subCategory; // Áo, Quần, etc.

    @Column(nullable = false)
    private Double price;

    @Column
    private String description;

    @Column
    private String imageUrl;

    @Column
    private Integer stock;

    @Column
    private Boolean isNew;

    @Column
    private Boolean isSale;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}