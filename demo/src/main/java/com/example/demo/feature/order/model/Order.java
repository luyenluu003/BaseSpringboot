package com.example.demo.feature.order.model;

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
@Table(name = "orders")
public class Order implements Serializable {
    private static final long serialVersionUID = 1231239774024L;

    @Id
    @Column
    private String orderId;

    @Column
    private String userId;

    @Column
    private Double totalAmount;

    @Column
    private String status; // PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED

    @Column
    private String shippingAddress;

    @Column
    private String phoneNumber;

    @Column
    private String paymentMethod;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}