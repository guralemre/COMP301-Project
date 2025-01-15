package com.comp301project.ecommerce.service;

import com.comp301project.ecommerce.dto.RegisterRequest;
import com.comp301project.ecommerce.model.User;

public interface UserService {
    User registerUser(RegisterRequest request);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 