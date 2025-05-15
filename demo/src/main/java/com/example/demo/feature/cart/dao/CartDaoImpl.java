package com.example.demo.feature.cart.dao;

import com.example.demo.feature.cart.model.Cart;
import com.example.demo.feature.cart.model.CartItem;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Cart findCartByUserId(String userId) {
        try {
            TypedQuery<Cart> query = entityManager.createQuery(
                "SELECT c FROM Cart c WHERE c.userId = :userId", Cart.class);
            query.setParameter("userId", userId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public Cart saveCart(Cart cart) {
        if (cart.getCartId() != null && entityManager.find(Cart.class, cart.getCartId()) != null) {
            return entityManager.merge(cart);
        } else {
            entityManager.persist(cart);
            return cart;
        }
    }

    @Override
    public List<CartItem> findCartItems(String cartId) {
        TypedQuery<CartItem> query = entityManager.createQuery(
            "SELECT ci FROM CartItem ci WHERE ci.cartId = :cartId", CartItem.class);
        query.setParameter("cartId", cartId);
        return query.getResultList();
    }

    @Override
    public CartItem findCartItemByProductId(String cartId, String productId) {
        try {
            TypedQuery<CartItem> query = entityManager.createQuery(
                "SELECT ci FROM CartItem ci WHERE ci.cartId = :cartId AND ci.productId = :productId", 
                CartItem.class);
            query.setParameter("cartId", cartId);
            query.setParameter("productId", productId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public CartItem saveCartItem(CartItem cartItem) {
        if (cartItem.getCartItemId() != null && entityManager.find(CartItem.class, cartItem.getCartItemId()) != null) {
            return entityManager.merge(cartItem);
        } else {
            entityManager.persist(cartItem);
            return cartItem;
        }
    }

    @Override
    @Transactional
    public void deleteCartItem(String cartItemId) {
        CartItem cartItem = entityManager.find(CartItem.class, cartItemId);
        if (cartItem != null) {
            entityManager.remove(cartItem);
        }
    }

    @Override
    @Transactional
    public void deleteAllCartItems(String cartId) {
        entityManager.createQuery(
            "DELETE FROM CartItem ci WHERE ci.cartId = :cartId")
            .setParameter("cartId", cartId)
            .executeUpdate();
    }
}