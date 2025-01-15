package com.comp301project.ecommerce.service;

import com.comp301project.ecommerce.model.CartItem;
import com.comp301project.ecommerce.model.Product;
import com.comp301project.ecommerce.model.User;
import com.comp301project.ecommerce.repository.CartItemRepository;
import com.comp301project.ecommerce.repository.ProductRepository;
import com.comp301project.ecommerce.repository.UserRepository;
import com.comp301project.ecommerce.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;

    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartService = new CartServiceImpl(cartItemRepository, userRepository, productRepository);
    }

    @Test
    void addToCart() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartItemRepository.findByUserAndProduct(user, product)).thenReturn(null);

        // Act
        CartItem cartItem = cartService.addToCart(1L, 1, "testuser");

        // Assert
        assertNotNull(cartItem);
        assertEquals(product, cartItem.getProduct());
        assertEquals(user, cartItem.getUser());
        assertEquals(1, cartItem.getQuantity());
    }

    @Test
    void getCartItems() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");

        CartItem item = new CartItem();
        item.setUser(user);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(cartItemRepository.findByUser(user)).thenReturn(Arrays.asList(item));

        // Act
        var items = cartService.getCartItems("testuser");

        // Assert
        assertEquals(1, items.size());
    }
} 