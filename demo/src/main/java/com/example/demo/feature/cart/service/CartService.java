package com.example.demo.feature.cart.service;

import com.example.demo.feature.cart.model.Cart;
import com.example.demo.feature.cart.model.CartItem;
import java.util.List;

public interface CartService {
    Cart getCartByUserId(String userId);
    Cart saveCart(Cart cart);
    List<CartItem> getCartItems(String cartId);
    CartItem getCartItemByProductId(String cartId, String productId);
    CartItem saveCartItem(CartItem cartItem);
    void removeCartItem(String cartItemId);
    void clearCart(String cartId);
}