package com.example.demo.feature.product.dao;

import com.example.demo.feature.product.model.Product;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductDao {
    Product findById(String productId);
    List<Product> findAll();
    List<Product> findByCategory(String category);
    List<Product> findBySubCategory(String subCategory);
    List<Product> findNewProducts();
    List<Product> findSaleProducts();
    Product save(Product product);
    void delete(String productId);
}