// package com.example.demo.util;

// import com.example.demo.feature.product.model.Product;
// import com.example.demo.feature.product.service.ProductService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import java.util.Date;
// import java.util.UUID;

// @Component
// public class DataInitializer implements CommandLineRunner {

//     @Autowired
//     private ProductService productService;

//     @Override
//     public void run(String... args) throws Exception {
        
//         addProduct("Áo Nam Tay Ngắn", "men", "Áo", 199000.0, 
//                 "Áo nam tay ngắn chất liệu cotton", 
//                 "https://blog.totoday.vn/wp-content/uploads/2022/10/Bat-mi-6-cach-phoi-ao-so-mi-nam-tay-ngan-thoi-trang-dep-mien-che-9.jpg", 100, true, false);
        
//         addProduct("Áo Len Dáng Cơ Bản", "men", "Áo", 499000.0, 
//                 "Áo len dáng cơ bản màu xanh rêu", 
//                 "https://pos.nvncdn.com/fa2431-2286/ps/20250108_55ICAQQQ0o.jpeg", 50, false, false);
        
//         addProduct("Áo Len Nam Gile", "men", "Áo", 399000.0, 
//                 "Áo len nam kiểu gile không tay", 
//                 "https://product.hstatic.net/1000402464/product/sw23fh07c-_gl_grey____1__89e3036a4a9f4fa4afff2ad8401f1147_master.jpg", 30, false, true);
        
//         addProduct("Áo Len Nam", "men", "Áo", 599000.0, 
//                 "Áo len nam màu nâu cổ cao", 
//                 "https://product.hstatic.net/200000886795/product/dsc07615_59cbe3e5297740a0bde969647e5c9c47.jpg", 40, true, false);

        
//         addProduct("Áo Thun Dài Nữ", "women", "Áo", 269000.0, 
//                 "Áo thun dài tay nữ màu xanh navy", 
//                 "https://bizweb.dktcdn.net/100/366/703/products/dsc-1554.jpg?v=1665661728623", 80, false, true);
        
//         addProduct("Áo Len Cổ Tim", "women", "Áo", 399000.0, 
//                 "Áo len cổ tim nữ màu hồng", 
//                 "https://media.phunutoday.vn/files/luonghoa/2018/11/27/00-1531.jpg", 60, true, false);
        
//         addProduct("Áo Len Tay Lửng Nữ", "women", "Áo", 299000.0, 
//                 "Áo len tay lửng nữ màu đen", 
//                 "https://bizweb.dktcdn.net/thumb/1024x1024/100/403/511/products/o1cn016ntfeg1kecnbx15di2705954.jpg", 70, false, false);
        
//         addProduct("Áo Len Nữ", "women", "Áo", 349000.0, 
//                 "Áo len nữ màu vàng", 
//                 "https://bizweb.dktcdn.net/thumb/1024x1024/100/119/564/products/ao-len-nu-han-quoc-3147.jpg?v=1732697107833", 45, true, true);

//         addProduct("Áo nam tay dài", "children", "Áo", 269000.0, 
//         "Áo nam tay dài", 
//         "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ9IJarnWYPQaUFJQxwUvO6F28sY7GveaukNg&s", 80, false, true);
        
//         addProduct("Áo nữ tay ngắn", "children", "Áo", 399000.0, 
//                 "Áo nữ tay ngắn", 
//                 "https://ardilla.com.vn/wp-content/uploads/2021/06/T163GS21_a.jpg", 60, true, false);
        
//         addProduct("Áo nam tay ngắn", "children", "Áo", 299000.0, 
//                 "Áo nam tay ngắn", 
//                 "https://cdn.becungshop.vn/images/300x300/bo-be-trai-gom-ao-so-mi-tay-ke-soc-ngan-va-quan-short-sanh-dieu-p280803e7618ee-300x300.jpg", 70, false, false);
        
//         addProduct("Áo Len Nữ tay dài", "children", "Áo", 349000.0, 
//                 "Áo Len Nữ tay dài", 
//                 "https://ardilla.com.vn/wp-content/uploads/2020/10/TA8704LDTA.jpg", 45, true, true);
//     }

//     private void addProduct(String name, String category, String subCategory, 
//                            Double price, String description, String imageUrl, 
//                            Integer stock, Boolean isNew, Boolean isSale) {
        
//         Product product = Product.builder()
//                 .productId(UUID.randomUUID().toString())
//                 .name(name)
//                 .category(category)
//                 .subCategory(subCategory)
//                 .price(price)
//                 .description(description)
//                 .imageUrl(imageUrl)
//                 .stock(stock)
//                 .isNew(isNew)
//                 .isSale(isSale)
//                 .createdAt(new Date())
//                 .updatedAt(new Date())
//                 .build();
        
//         productService.saveProduct(product);
//     }
// }
