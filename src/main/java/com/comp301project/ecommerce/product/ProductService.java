package com.comp301project.ecommerce.product;


import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductService {
    
    @GetMapping
    public String getAllProducts() {
        return "Tüm ürünler listelendi";
    }
    
    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id) {
        return "Ürün detayı: " + id;
    }
} 