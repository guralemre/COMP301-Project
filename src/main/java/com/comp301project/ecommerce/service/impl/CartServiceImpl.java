package com.comp301project.ecommerce.service.impl;

import com.comp301project.ecommerce.model.CartItem;
import com.comp301project.ecommerce.model.Product;
import com.comp301project.ecommerce.model.User;
import com.comp301project.ecommerce.repository.CartItemRepository;
import com.comp301project.ecommerce.repository.ProductRepository;
import com.comp301project.ecommerce.repository.UserRepository;
import com.comp301project.ecommerce.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartItemRepository cartItemRepository,
                         UserRepository userRepository,
                         ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public CartItem addToCart(Long productId, Integer quantity, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

        CartItem existingItem = cartItemRepository.findByUserAndProduct(user, product);
        
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            return cartItemRepository.save(existingItem);
        }

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        
        return cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> getCartItems(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        return cartItemRepository.findByUser(user);
    }

    @Override
    @Transactional
    public void removeFromCart(Long cartItemId, String username) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Sepet öğesi bulunamadı"));
        
        if (!cartItem.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Bu işlem için yetkiniz yok");
        }
        
        cartItemRepository.delete(cartItem);
    }

    @Override
    @Transactional
    public void updateQuantity(Long cartItemId, Integer quantity, String username) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Sepet öğesi bulunamadı"));
        
        if (!cartItem.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Bu işlem için yetkiniz yok");
        }
        
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public void clearCart(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        cartItemRepository.deleteByUser(user);
    }

    @Override
    public Integer getCartItemCount(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        return cartItemRepository.countByUser(user);
    }
} 