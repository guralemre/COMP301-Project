package com.comp301project.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import com.comp301project.ecommerce.model.Product;
import com.comp301project.ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import com.comp301project.ecommerce.model.CartItem;
import com.comp301project.ecommerce.service.CartService;
import java.util.Map;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/shop/products")
public class ProductController {

    private final ProductService productService;
    private final CartService cartService;

    public ProductController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products/list";
    }

    @GetMapping("/{id}")
    public String getProductDetails(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "products/detail";
    }

    @PostMapping("/{id}/add-to-cart")
    public String addToCartFromHome(@PathVariable Long id, 
                                  @RequestParam(defaultValue = "1") Integer quantity,
                                  Authentication authentication,
                                  RedirectAttributes redirectAttributes) {
        try {
            if (authentication == null || !authentication.isAuthenticated() || 
                authentication.getPrincipal().equals("anonymousUser")) {
                return "redirect:/shop/login";
            }

            cartService.addToCart(id, quantity, authentication.getName());
            redirectAttributes.addFlashAttribute("success", "Ürün sepete eklendi");
            
            return "redirect:/shop";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/shop";
        }
    }

    @PostMapping("/{id}/add-to-cart/api")
    @ResponseBody
    public ResponseEntity<?> addToCartApi(@PathVariable Long id, 
                                        @RequestParam(defaultValue = "1") Integer quantity,
                                        Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated() || 
                authentication.getPrincipal().equals("anonymousUser")) {
                return ResponseEntity.status(401).body("Lütfen önce giriş yapın");
            }

            CartItem cartItem = cartService.addToCart(id, quantity, authentication.getName());
            Integer cartItemCount = cartService.getCartItemCount(authentication.getName());
            
            return ResponseEntity.ok(Map.of(
                "message", "Ürün sepete eklendi",
                "cartItemCount", cartItemCount
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(required = false) String query, Model model) {
        List<Product> products = productService.searchProducts(query);
        model.addAttribute("products", products);
        model.addAttribute("searchQuery", query);
        return "products/list";
    }
} 