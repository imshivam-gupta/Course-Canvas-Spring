package com.example.coursecanvasspring.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityFunctions {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String passwordEncoder(String password) {
        return encoder.encode(password);
    }

    public static boolean passwordMatch(String password, String encodedPassword) {
        return encoder.matches(password, encodedPassword);
    }
}
