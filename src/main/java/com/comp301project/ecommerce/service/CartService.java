package com.comp301project.ecommerce.service;

import com.comp301project.ecommerce.model.CartItem;
import java.util.List;

public interface CartService {
    CartItem addToCart(Long productId, Integer quantity, String username);
    List<CartItem> getCartItems(String username);
    void removeFromCart(Long cartItemId, String username);
    void updateQuantity(Long cartItemId, Integer quantity, String username);
    void clearCart(String username);
    Integer getCartItemCount(String username);
} 