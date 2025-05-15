package com.example.demo.feature.cart.service;

import com.example.demo.feature.cart.dao.CartDao;
import com.example.demo.feature.cart.model.Cart;
import com.example.demo.feature.cart.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Override
    public Cart getCartByUserId(String userId) {
        return cartDao.findCartByUserId(userId);
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartDao.saveCart(cart);
    }

    @Override
    public List<CartItem> getCartItems(String cartId) {
        return cartDao.findCartItems(cartId);
    }

    @Override
    public CartItem getCartItemByProductId(String cartId, String productId) {
        return cartDao.findCartItemByProductId(cartId, productId);
    }

    @Override
    public CartItem saveCartItem(CartItem cartItem) {
        return cartDao.saveCartItem(cartItem);
    }

    @Override
    public void removeCartItem(String cartItemId) {
        cartDao.deleteCartItem(cartItemId);
    }

    @Override
    public void clearCart(String cartId) {
        cartDao.deleteAllCartItems(cartId);
    }
}