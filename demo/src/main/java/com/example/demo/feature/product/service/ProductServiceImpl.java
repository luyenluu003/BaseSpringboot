package com.example.demo.feature.product.service;

import com.example.demo.feature.product.dao.ProductDao;
import com.example.demo.feature.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(String productId) {
        return productDao.findById(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productDao.findByCategory(category);
    }

    @Override
    public List<Product> getProductsBySubCategory(String subCategory) {
        return productDao.findBySubCategory(subCategory);
    }

    @Override
    public List<Product> getNewProducts() {
        return productDao.findNewProducts();
    }

    @Override
    public List<Product> getSaleProducts() {
        return productDao.findSaleProducts();
    }

    @Override
    public Product saveProduct(Product product) {
        return productDao.save(product);
    }

    @Override
    public void deleteProduct(String productId) {
        productDao.delete(productId);
    }
}