package com.comp301project.ecommerce.service;

import com.comp301project.ecommerce.model.Product;
import com.comp301project.ecommerce.repository.ProductRepository;
import com.comp301project.ecommerce.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void getAllProducts() {
        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Test Product 1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Test Product 2");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // Act
        List<Product> products = productService.getAllProducts();

        // Assert
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        Product foundProduct = productService.getProductById(1L);

        // Assert
        assertNotNull(foundProduct);
        assertEquals("Test Product", foundProduct.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void searchProducts() {
        // Arrange
        Product product = new Product();
        product.setName("iPhone");
        product.setDescription("Test iPhone");

        when(productRepository.searchProducts("iPhone"))
            .thenReturn(Arrays.asList(product));

        // Act
        List<Product> results = productService.searchProducts("iPhone");

        // Assert
        assertEquals(1, results.size());
        assertEquals("iPhone", results.get(0).getName());
    }
} 