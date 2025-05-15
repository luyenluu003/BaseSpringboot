package com.example.demo.feature.order.dao;

import com.example.demo.feature.order.model.Order;
import com.example.demo.feature.order.model.OrderItem;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order findOrderById(String orderId) {
        return entityManager.find(Order.class, orderId);
    }

    @Override
    public List<Order> findOrdersByUserId(String userId) {
        TypedQuery<Order> query = entityManager.createQuery(
            "SELECT o FROM Order o WHERE o.userId = :userId ORDER BY o.createdAt DESC", 
            Order.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Order saveOrder(Order order) {
        if (order.getOrderId() != null && entityManager.find(Order.class, order.getOrderId()) != null) {
            return entityManager.merge(order);
        } else {
            entityManager.persist(order);
            return order;
        }
    }

    @Override
    public List<OrderItem> findOrderItems(String orderId) {
        TypedQuery<OrderItem> query = entityManager.createQuery(
            "SELECT oi FROM OrderItem oi WHERE oi.orderId = :orderId", 
            OrderItem.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public OrderItem saveOrderItem(OrderItem orderItem) {
        if (orderItem.getOrderItemId() != null && entityManager.find(OrderItem.class, orderItem.getOrderItemId()) != null) {
            return entityManager.merge(orderItem);
        } else {
            entityManager.persist(orderItem);
            return orderItem;
        }
    }
}