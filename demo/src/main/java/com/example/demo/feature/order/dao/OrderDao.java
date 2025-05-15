package com.example.demo.feature.order.dao;

import com.example.demo.feature.order.model.Order;
import com.example.demo.feature.order.model.OrderItem;
import java.util.List;

public interface OrderDao {
    Order findOrderById(String orderId);
    List<Order> findOrdersByUserId(String userId);
    Order saveOrder(Order order);
    List<OrderItem> findOrderItems(String orderId);
    OrderItem saveOrderItem(OrderItem orderItem);
}