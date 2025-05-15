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
            // Tạo giỏ hàng mới nếu chưa có
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
        
        // Kiểm tra sản phẩm tồn tại
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Product not found"
            ));
        }

        // Kiểm tra số lượng
        if (quantity <= 0) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Quantity must be greater than 0"
            ));
        }

        // Lấy giỏ hàng hiện tại hoặc tạo mới
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

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        CartItem existingItem = cartService.getCartItemByProductId(cart.getCartId(), productId);
        
        if (existingItem != null) {
            // Cập nhật số lượng
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartService.saveCartItem(existingItem);
        } else {
            // Thêm mới vào giỏ hàng
            CartItem cartItem = CartItem.builder()
                    .cartItemId(UUID.randomUUID().toString())
                    .cartId(cart.getCartId())
                    .productId(productId)
                    .quantity(quantity)
                    .price(product.getPrice())
                    .build();
            cartService.saveCartItem(cartItem);
        }

        // Cập nhật thời gian giỏ hàng
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
        
        // Kiểm tra số lượng
        if (quantity < 0) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Quantity cannot be negative"
            ));
        }

        // Lấy giỏ hàng
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Cart not found"
            ));
        }

        // Lấy item trong giỏ hàng
        CartItem cartItem = cartService.getCartItemByProductId(cart.getCartId(), productId);
        if (cartItem == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Product not found in cart"
            ));
        }

        if (quantity == 0) {
            // Xóa sản phẩm khỏi giỏ hàng
            cartService.removeCartItem(cartItem.getCartItemId());
        } else {
            // Cập nhật số lượng
            cartItem.setQuantity(quantity);
            cartService.saveCartItem(cartItem);
        }

        // Cập nhật thời gian giỏ hàng
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
        
        // Lấy giỏ hàng
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Cart not found"
            ));
        }

        // Lấy item trong giỏ hàng
        CartItem cartItem = cartService.getCartItemByProductId(cart.getCartId(), productId);
        if (cartItem == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Product not found in cart"
            ));
        }

        // Xóa sản phẩm khỏi giỏ hàng
        cartService.removeCartItem(cartItem.getCartItemId());

        // Cập nhật thời gian giỏ hàng
        cart.setUpdatedAt(new Date());
        cartService.saveCart(cart);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Product removed from cart successfully"
        ));
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable String userId) {
        // Lấy giỏ hàng
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Cart not found"
            ));
        }

        // Xóa tất cả sản phẩm trong giỏ hàng
        cartService.clearCart(cart.getCartId());

        // Cập nhật thời gian giỏ hàng
        cart.setUpdatedAt(new Date());
        cartService.saveCart(cart);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Cart cleared successfully"
        ));
    }
}