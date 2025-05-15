package com.example.demo.api.v1;

import com.example.demo.feature.cart.model.Cart;
import com.example.demo.feature.cart.model.CartItem;
import com.example.demo.feature.cart.service.CartService;
import com.example.demo.feature.order.model.Order;
import com.example.demo.feature.order.model.OrderItem;
import com.example.demo.feature.order.service.OrderService;
import com.example.demo.feature.product.model.Product;
import com.example.demo.feature.product.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Log4j2
@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserOrders(@PathVariable String userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        Map<String, List<OrderItem>> orderItems = new HashMap<>();
        
        for (Order order : orders) {
            orderItems.put(order.getOrderId(), orderService.getOrderItems(order.getOrderId()));
        }
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", Map.of(
                "orders", orders,
                "orderItems", orderItems
            )
        ));
    }

    @GetMapping("/details/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Order not found"
            ));
        }
        
        List<OrderItem> orderItems = orderService.getOrderItems(orderId);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", Map.of(
                "order", order,
                "items", orderItems
            )
        ));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(
            @RequestParam String userId,
            @RequestParam String shippingAddress,
            @RequestParam String phoneNumber,
            @RequestParam String paymentMethod) {
        
        // Lấy giỏ hàng
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Cart not found"
            ));
        }
        
        List<CartItem> cartItems = cartService.getCartItems(cart.getCartId());
        if (cartItems.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Cart is empty"
            ));
        }
        
        // Tính tổng tiền
        double totalAmount = 0;
        for (CartItem item : cartItems) {
            Product product = productService.getProductById(item.getProductId());
            if (product != null) {
                totalAmount += product.getPrice() * item.getQuantity();
            }
        }
        
        // Tạo đơn hàng
        Order order = Order.builder()
                .orderId(UUID.randomUUID().toString())
                .userId(userId)
                .totalAmount(totalAmount)
                .status("PENDING")
                .shippingAddress(shippingAddress)
                .phoneNumber(phoneNumber)
                .paymentMethod(paymentMethod)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        
        orderService.saveOrder(order);
        
        // Tạo các mục đơn hàng
        for (CartItem item : cartItems) {
            Product product = productService.getProductById(item.getProductId());
            if (product != null) {
                OrderItem orderItem = OrderItem.builder()
                        .orderItemId(UUID.randomUUID().toString())
                        .orderId(order.getOrderId())
                        .productId(item.getProductId())
                        .productName(product.getName())
                        .quantity(item.getQuantity())
                        .price(product.getPrice())
                        .build();
                
                orderService.saveOrderItem(orderItem);
            }
        }
        
        // Xóa giỏ hàng sau khi đặt hàng
        cartService.clearCart(cart.getCartId());
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Order created successfully",
            "data", Map.of(
                "order", order
            )
        ));
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Order not found"
            ));
        }
        
        // Chỉ có thể hủy đơn hàng ở trạng thái PENDING hoặc PROCESSING
        if (!order.getStatus().equals("PENDING") && !order.getStatus().equals("PROCESSING")) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Cannot cancel order in current status: " + order.getStatus()
            ));
        }
        
        order.setStatus("CANCELLED");
        order.setUpdatedAt(new Date());
        orderService.saveOrder(order);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Order cancelled successfully",
            "data", order
        ));
    }

    @PutMapping("/status/{orderId}")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable String orderId,
            @RequestParam String status) {
        
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Order not found"
            ));
        }
        
        // Kiểm tra trạng thái hợp lệ
        List<String> validStatuses = Arrays.asList("PENDING", "PROCESSING", "SHIPPED", "DELIVERED", "CANCELLED");
        if (!validStatuses.contains(status)) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Invalid status: " + status
            ));
        }
        
        order.setStatus(status);
        order.setUpdatedAt(new Date());
        orderService.saveOrder(order);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Order status updated successfully",
            "data", order
        ));
    }
}
