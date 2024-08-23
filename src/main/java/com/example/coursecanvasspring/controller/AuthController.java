package com.example.coursecanvasspring.controller;

import com.example.coursecanvasspring.dto.JWTAuthResponse;
import com.example.coursecanvasspring.dto.LoginRequest;
import com.example.coursecanvasspring.dto.SignupRequest;
import com.example.coursecanvasspring.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<JWTAuthResponse> registerUser(@RequestBody SignupRequest signupRequest) {
        JWTAuthResponse jwtAuthResponse = authService.registerUser(signupRequest);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JWTAuthResponse jwtAuthResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtAuthResponse);
    }
}
