package com.comp301project.ecommerce.service;

import com.comp301project.ecommerce.model.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    List<Product> searchProducts(String keyword);
} 