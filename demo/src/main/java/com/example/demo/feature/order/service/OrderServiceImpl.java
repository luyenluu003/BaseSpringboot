package com.example.demo.feature.order.service;

import com.example.demo.feature.order.dao.OrderDao;
import com.example.demo.feature.order.model.Order;
import com.example.demo.feature.order.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public Order getOrderById(String orderId) {
        return orderDao.findOrderById(orderId);
    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        return orderDao.findOrdersByUserId(userId);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderDao.saveOrder(order);
    }

    @Override
    public List<OrderItem> getOrderItems(String orderId) {
        return orderDao.findOrderItems(orderId);
    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderDao.saveOrderItem(orderItem);
    }
}