package com.comp301project.ecommerce.config;

import com.comp301project.ecommerce.model.Product;
import com.comp301project.ecommerce.model.User;
import com.comp301project.ecommerce.repository.ProductRepository;
import com.comp301project.ecommerce.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, 
                                 ProductRepository productRepository,
                                 PasswordEncoder passwordEncoder) {
        return args -> {
            // Kullanıcılar
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@eticaret.com");
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setEmail("user@eticaret.com");
            user.setFirstName("Test");
            user.setLastName("User");
            userRepository.save(user);

            // Örnek Ürünler
            Product laptop = new Product();
            laptop.setName("MacBook Pro M2");
            laptop.setDescription("Apple M2 çip, 16GB RAM, 512GB SSD");
            laptop.setPrice(39999.99);
            laptop.setImageUrl("https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/mbp-spacegray-select-202206?wid=904&hei=840&fmt=jpeg&qlt=90&.v=1664497359481");
            productRepository.save(laptop);

            Product phone = new Product();
            phone.setName("iPhone 15 Pro");
            phone.setDescription("A17 Pro çip, 256GB, Titanium");
            phone.setPrice(64999.99);
            phone.setImageUrl("https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/iphone-15-pro-finish-select-202309-6-1inch-naturaltitanium?wid=5120&hei=2880&fmt=p-jpg&qlt=80&.v=1692845702708");
            productRepository.save(phone);

            Product tablet = new Product();
            tablet.setName("iPad Air");
            tablet.setDescription("M1 çip, 10.9-inç Liquid Retina ekran");
            tablet.setPrice(24999.99);
            tablet.setImageUrl("https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/ipad-air-select-wifi-blue-202203?wid=940&hei=1112&fmt=png-alpha&.v=1645065732688");
            productRepository.save(tablet);

            Product watch = new Product();
            watch.setName("Apple Watch Series 9");
            watch.setDescription("45mm, Alüminyum Kasa");
            watch.setPrice(14999.99);
            watch.setImageUrl("https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/watch-case-45-aluminum-midnight-nc-s9_VW_PF_WF_SI?wid=1400&hei=1400&fmt=png-alpha&.v=1692936829206");
            productRepository.save(watch);

            Product airpods = new Product();
            airpods.setName("AirPods Pro 2");
            airpods.setDescription("Aktif Gürültü Önleme, Adaptif Ses");
            airpods.setPrice(7999.99);
            airpods.setImageUrl("https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/MQD83?wid=1144&hei=1144&fmt=jpeg&qlt=90&.v=1660803972361");
            productRepository.save(airpods);
        };
    }
} 