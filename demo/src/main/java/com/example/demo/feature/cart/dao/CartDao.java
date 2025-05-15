package com.example.demo.feature.cart.dao;

import com.example.demo.feature.cart.model.Cart;
import com.example.demo.feature.cart.model.CartItem;
import java.util.List;

public interface CartDao {
    Cart findCartByUserId(String userId);
    Cart saveCart(Cart cart);
    List<CartItem> findCartItems(String cartId);
    CartItem findCartItemByProductId(String cartId, String productId);
    CartItem saveCartItem(CartItem cartItem);
    void deleteCartItem(String cartItemId);
    void deleteAllCartItems(String cartId);
}