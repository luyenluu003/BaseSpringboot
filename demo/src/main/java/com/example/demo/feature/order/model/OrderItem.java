package com.example.demo.feature.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1231239774025L;

    @Id
    @Column
    private String orderItemId;

    @Column
    private String orderId;

    @Column
    private String productId;

    @Column
    private String productName;

    @Column
    private Integer quantity;

    @Column
    private Double price;
}