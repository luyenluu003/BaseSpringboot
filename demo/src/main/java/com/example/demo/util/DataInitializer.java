package com.example.demo.util;

import com.example.demo.feature.product.model.Product;
import com.example.demo.feature.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        // Thêm sản phẩm mẫu cho Nam
        addProduct("Áo Nam Tay Ngắn", "Nam", "Áo", 199000.0, 
                "Áo nam tay ngắn chất liệu cotton", 
                "https://example.com/images/ao-nam-tay-ngan.jpg", 100, true, false);
        
        addProduct("Áo Len Dáng Cơ Bản", "Nam", "Áo", 499000.0, 
                "Áo len dáng cơ bản màu xanh rêu", 
                "https://example.com/images/ao-len-dang-co-ban.jpg", 50, false, false);
        
        addProduct("Áo Len Nam Gile", "Nam", "Áo", 399000.0, 
                "Áo len nam kiểu gile không tay", 
                "https://example.com/images/ao-len-nam-gile.jpg", 30, false, true);
        
        addProduct("Áo Len Nam", "Nam", "Áo", 599000.0, 
                "Áo len nam màu nâu cổ cao", 
                "https://example.com/images/ao-len-nam.jpg", 40, true, false);

        // Thêm sản phẩm mẫu cho Nữ
        addProduct("Áo Thun Dài Nữ", "Nữ", "Áo", 269000.0, 
                "Áo thun dài tay nữ màu xanh navy", 
                "https://example.com/images/ao-thun-dai-nu.jpg", 80, false, true);
        
        addProduct("Áo Len Cổ Tim", "Nữ", "Áo", 399000.0, 
                "Áo len cổ tim nữ màu hồng", 
                "https://example.com/images/ao-len-co-tim.jpg", 60, true, false);
        
        addProduct("Áo Len Tay Lửng Nữ", "Nữ", "Áo", 299000.0, 
                "Áo len tay lửng nữ màu đen", 
                "https://example.com/images/ao-len-tay-lung-nu.jpg", 70, false, false);
        
        addProduct("Áo Len Nữ", "Nữ", "Áo", 349000.0, 
                "Áo len nữ màu vàng", 
                "https://example.com/images/ao-len-nu.jpg", 45, true, true);
    }

    private void addProduct(String name, String category, String subCategory, 
                           Double price, String description, String imageUrl, 
                           Integer stock, Boolean isNew, Boolean isSale) {
        
        Product product = Product.builder()
                .productId(UUID.randomUUID().toString())
                .name(name)
                .category(category)
                .subCategory(subCategory)
                .price(price)
                .description(description)
                .imageUrl(imageUrl)
                .stock(stock)
                .isNew(isNew)
                .isSale(isSale)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        
        productService.saveProduct(product);
    }
}