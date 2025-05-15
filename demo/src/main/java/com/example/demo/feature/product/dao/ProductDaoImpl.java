package com.example.demo.feature.product.dao;

import com.example.demo.feature.product.model.Product;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Product findById(String productId) {
        return entityManager.find(Product.class, productId);
    }

    @Override
    public List<Product> findAll() {
        TypedQuery<Product> query = entityManager.createQuery(
            "SELECT p FROM Product p ORDER BY p.createdAt DESC", Product.class);
        return query.getResultList();
    }

    @Override
    public List<Product> findByCategory(String category) {
        TypedQuery<Product> query = entityManager.createQuery(
            "SELECT p FROM Product p WHERE p.category = :category ORDER BY p.createdAt DESC", 
            Product.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    @Override
    public List<Product> findBySubCategory(String subCategory) {
        TypedQuery<Product> query = entityManager.createQuery(
            "SELECT p FROM Product p WHERE p.subCategory = :subCategory ORDER BY p.createdAt DESC", 
            Product.class);
        query.setParameter("subCategory", subCategory);
        return query.getResultList();
    }

    @Override
    public List<Product> findNewProducts() {
        TypedQuery<Product> query = entityManager.createQuery(
            "SELECT p FROM Product p WHERE p.isNew = true ORDER BY p.createdAt DESC", 
            Product.class);
        return query.getResultList();
    }

    @Override
    public List<Product> findSaleProducts() {
        TypedQuery<Product> query = entityManager.createQuery(
            "SELECT p FROM Product p WHERE p.isSale = true ORDER BY p.createdAt DESC", 
            Product.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Product save(Product product) {
        if (product.getProductId() != null && entityManager.find(Product.class, product.getProductId()) != null) {
            return entityManager.merge(product);
        } else {
            entityManager.persist(product);
            return product;
        }
    }

    @Override
    @Transactional
    public void delete(String productId) {
        Product product = entityManager.find(Product.class, productId);
        if (product != null) {
            entityManager.remove(product);
        }
    }
}