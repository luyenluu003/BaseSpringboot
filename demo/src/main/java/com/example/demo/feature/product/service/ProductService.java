package com.example.demo.feature.product.service;

import com.example.demo.feature.product.model.Product;
import java.util.List;

public interface ProductService {
    Product getProductById(String productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsBySubCategory(String subCategory);
    List<Product> getNewProducts();
    List<Product> getSaleProducts();
    Product saveProduct(Product product);
    void deleteProduct(String productId);
}