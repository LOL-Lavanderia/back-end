package com.example.demo.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService{

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String hashPasswordWithSalt(String password, String salt) {
        return passwordEncoder.encode(password + salt);
    }

    public String generateSalt() {
        return BCrypt.gensalt();
    }
}