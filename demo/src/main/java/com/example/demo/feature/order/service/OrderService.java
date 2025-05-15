package com.example.demo.feature.order.service;

import com.example.demo.feature.order.model.Order;
import com.example.demo.feature.order.model.OrderItem;
import java.util.List;

public interface OrderService {
    Order getOrderById(String orderId);
    List<Order> getOrdersByUserId(String userId);
    Order saveOrder(Order order);
    List<OrderItem> getOrderItems(String orderId);
    OrderItem saveOrderItem(OrderItem orderItem);
}