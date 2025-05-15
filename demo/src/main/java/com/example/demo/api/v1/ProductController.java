package com.example.demo.api.v1;

import com.example.demo.feature.product.model.Product;
import com.example.demo.feature.product.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", products
        ));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String productId) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Product not found"
            ));
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", product
        ));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", products
        ));
    }

    @GetMapping("/subcategory/{subCategory}")
    public ResponseEntity<?> getProductsBySubCategory(@PathVariable String subCategory) {
        List<Product> products = productService.getProductsBySubCategory(subCategory);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", products
        ));
    }

    @GetMapping("/new")
    public ResponseEntity<?> getNewProducts() {
        List<Product> products = productService.getNewProducts();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", products
        ));
    }

    @GetMapping("/sale")
    public ResponseEntity<?> getSaleProducts() {
        List<Product> products = productService.getSaleProducts();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", products
        ));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product productRequest) {
        // Tạo sản phẩm mới
        Product product = Product.builder()
            .productId(UUID.randomUUID().toString())
            .name(productRequest.getName())
            .category(productRequest.getCategory())
            .subCategory(productRequest.getSubCategory())
            .price(productRequest.getPrice())
            .description(productRequest.getDescription())
            .imageUrl(productRequest.getImageUrl())
            .stock(productRequest.getStock())
            .isNew(productRequest.getIsNew())
            .isSale(productRequest.getIsSale())
            .createdAt(new Date())
            .updatedAt(new Date())
            .build();

        productService.saveProduct(product);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Product created successfully",
            "data", product
        ));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable String productId,
            @RequestBody Product productRequest) {
        
        Product existingProduct = productService.getProductById(productId);
        if (existingProduct == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Product not found"
            ));
        }

        // Cập nhật thông tin sản phẩm
        existingProduct.setName(productRequest.getName());
        existingProduct.setCategory(productRequest.getCategory());
        existingProduct.setSubCategory(productRequest.getSubCategory());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setImageUrl(productRequest.getImageUrl());
        existingProduct.setStock(productRequest.getStock());
        existingProduct.setIsNew(productRequest.getIsNew());
        existingProduct.setIsSale(productRequest.getIsSale());
        existingProduct.setUpdatedAt(new Date());

        productService.saveProduct(existingProduct);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Product updated successfully",
            "data", existingProduct
        ));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId) {
        Product existingProduct = productService.getProductById(productId);
        if (existingProduct == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Product not found"
            ));
        }

        productService.deleteProduct(productId);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Product deleted successfully"
        ));
    }
}