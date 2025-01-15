package com.comp301project.ecommerce.order;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderService {
    
    @PostMapping
    public String createOrder() {
        return "Sipariş oluşturuldu";
    }
    
    @GetMapping("/{id}")
    public String getOrder(@PathVariable Long id) {
        return "Sipariş detayı: " + id;
    }
} 