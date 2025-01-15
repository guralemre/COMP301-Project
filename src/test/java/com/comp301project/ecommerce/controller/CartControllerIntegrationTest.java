package com.comp301project.ecommerce.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testuser")
    void viewCart() throws Exception {
        mockMvc.perform(get("/shop/cart"))
               .andExpect(status().isOk())
               .andExpect(view().name("cart/cart"));
    }

    @Test
    void viewCartUnauthenticated() throws Exception {
        mockMvc.perform(get("/shop/cart"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("http://localhost/shop/login"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void updateCartItem() throws Exception {
        mockMvc.perform(post("/shop/cart/update/1")
                .param("quantity", "2"))
               .andExpect(status().isOk());
    }
} 