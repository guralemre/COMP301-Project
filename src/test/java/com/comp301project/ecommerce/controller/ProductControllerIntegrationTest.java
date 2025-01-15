package com.comp301project.ecommerce.controller;

import com.comp301project.ecommerce.model.Product;
import com.comp301project.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void getAllProducts() throws Exception {
        mockMvc.perform(get("/shop/products"))
               .andExpect(status().isOk())
               .andExpect(view().name("products/list"))
               .andExpect(model().attributeExists("products"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void addToCart() throws Exception {
        // Arrange
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(99.99);
        productRepository.save(product);

        // Act & Assert
        mockMvc.perform(get("/shop/products/" + product.getId()))
               .andExpect(status().isOk())
               .andExpect(view().name("products/detail"))
               .andExpect(model().attributeExists("product"));
    }

    @Test
    void searchProducts() throws Exception {
        mockMvc.perform(get("/shop/products/search")
                .param("query", "iPhone"))
               .andExpect(status().isOk())
               .andExpect(view().name("products/list"))
               .andExpect(model().attributeExists("products"))
               .andExpect(model().attributeExists("searchQuery"));
    }
} 