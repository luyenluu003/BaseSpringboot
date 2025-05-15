package com.example.demo.feature.cart.model;

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
@Table(name = "cart_items")
public class CartItem implements Serializable {
    private static final long serialVersionUID = 1231239774022L;

    @Id
    @Column
    private String cartItemId;

    @Column
    private String cartId;

    @Column
    private String productId;

    @Column
    private Integer quantity;

    @Column
    private Double price;
}