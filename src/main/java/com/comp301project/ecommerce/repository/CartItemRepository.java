package com.comp301project.ecommerce.repository;

import com.comp301project.ecommerce.model.CartItem;
import com.comp301project.ecommerce.model.Product;
import com.comp301project.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    CartItem findByUserAndProduct(User user, Product product);
    void deleteByUser(User user);
    Integer countByUser(User user);
} 