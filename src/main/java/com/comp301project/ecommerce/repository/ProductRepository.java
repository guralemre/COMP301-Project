package com.comp301project.ecommerce.repository;

import com.comp301project.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(concat('%', ?1, '%')) OR LOWER(p.description) LIKE LOWER(concat('%', ?1, '%'))")
    List<Product> searchProducts(String keyword);
} 