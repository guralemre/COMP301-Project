package com.comp301project.ecommerce.controller;

import com.comp301project.ecommerce.model.CartItem;
import com.comp301project.ecommerce.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Controller
@RequestMapping("/shop/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String viewCart(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() && 
            !authentication.getPrincipal().equals("anonymousUser")) {
            List<CartItem> cartItems = cartService.getCartItems(authentication.getName());
            double total = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
            
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("total", total);
        }
        return "cart/cart";
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<?> updateQuantity(@PathVariable Long id,
                                          @RequestParam Integer quantity,
                                          Authentication authentication) {
        try {
            cartService.updateQuantity(id, quantity, authentication.getName());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/remove/{id}")
    @ResponseBody
    public ResponseEntity<?> removeItem(@PathVariable Long id,
                                      Authentication authentication) {
        try {
            cartService.removeFromCart(id, authentication.getName());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 