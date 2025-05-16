package com.example.demo.api.v1;

import com.example.demo.feature.cart.model.Cart;
import com.example.demo.feature.cart.model.CartItem;
import com.example.demo.feature.cart.service.CartService;
import com.example.demo.feature.product.model.Product;
import com.example.demo.feature.product.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCart(@PathVariable String userId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            
            cart = Cart.builder()
                    .cartId(UUID.randomUUID().toString())
                    .userId(userId)
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();
            cartService.saveCart(cart);
        }

        List<CartItem> cartItems = cartService.getCartItems(cart.getCartId());
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", Map.of(
                "cart", cart,
                "items", cartItems
            )
        ));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @RequestParam String userId,
            @RequestParam String productId,
            @RequestParam Integer quantity) {
        
        
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Product not found"
            ));
        }

        
        if (quantity <= 0) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Quantity must be greater than 0"
            ));
        }

        
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            cart = Cart.builder()
                    .cartId(UUID.randomUUID().toString())
                    .userId(userId)
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();
            cartService.saveCart(cart);
        }

        
        CartItem existingItem = cartService.getCartItemByProductId(cart.getCartId(), productId);
        
        if (existingItem != null) {
            
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartService.saveCartItem(existingItem);
        } else {
            
            CartItem cartItem = CartItem.builder()
                    .cartItemId(UUID.randomUUID().toString())
                    .cartId(cart.getCartId())
                    .productId(productId)
                    .quantity(quantity)
                    .price(product.getPrice())
                    .build();
            cartService.saveCartItem(cartItem);
        }

        
        cart.setUpdatedAt(new Date());
        cartService.saveCart(cart);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Product added to cart successfully"
        ));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCartItem(
            @RequestParam String userId,
            @RequestParam String productId,
            @RequestParam Integer quantity) {
        
        
        if (quantity < 0) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Quantity cannot be negative"
            ));
        }

        
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Cart not found"
            ));
        }

        
        CartItem cartItem = cartService.getCartItemByProductId(cart.getCartId(), productId);
        if (cartItem == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Product not found in cart"
            ));
        }

        if (quantity == 0) {
            
            cartService.removeCartItem(cartItem.getCartItemId());
        } else {
            
            cartItem.setQuantity(quantity);
            cartService.saveCartItem(cartItem);
        }

        
        cart.setUpdatedAt(new Date());
        cartService.saveCart(cart);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Cart updated successfully"
        ));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromCart(
            @RequestParam String userId,
            @RequestParam String productId) {
        
        
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Cart not found"
            ));
        }

        
        CartItem cartItem = cartService.getCartItemByProductId(cart.getCartId(), productId);
        if (cartItem == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Product not found in cart"
            ));
        }

        
        cartService.removeCartItem(cartItem.getCartItemId());

        
        cart.setUpdatedAt(new Date());
        cartService.saveCart(cart);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Product removed from cart successfully"
        ));
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable String userId) {
        
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Cart not found"
            ));
        }

        
        cartService.clearCart(cart.getCartId());

        
        cart.setUpdatedAt(new Date());
        cartService.saveCart(cart);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Cart cleared successfully"
        ));
    }
}