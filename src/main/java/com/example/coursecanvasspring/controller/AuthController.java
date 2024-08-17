package com.example.coursecanvasspring.controller;

import com.example.coursecanvasspring.entity.user.User;
import com.example.coursecanvasspring.enums.UserRole;
import com.example.coursecanvasspring.dto.JWTAuthResponse;
import org.springframework.http.ResponseEntity;
import com.example.coursecanvasspring.dto.SignupRequest;
import com.example.coursecanvasspring.repository.UserRepository;
import com.example.coursecanvasspring.security.JWTProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTProvider jwtProvider;




    @PostMapping("/signup")
    public ResponseEntity<JWTAuthResponse> registerUser(@RequestBody SignupRequest signupRequest) {
        // Check if user already exists
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already taken!");
        }

        // Create new user
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(UserRole.STUDENT);
        user.setEmailVerified(false);

        // Save user to database
        userRepository.save(user);

        // Authenticate the user and generate JWT token
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signupRequest.getEmail(),
                        signupRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String jwt = jwtProvider.generateToken(user);

        // Return response with email and token
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse(jwt, user.getEmail());
        log.error("JWT: " + jwt);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/generateToken")
    public ResponseEntity<JWTAuthResponse> authenticateAndGetToken(@RequestBody SignupRequest signupRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signupRequest.getEmail(), signupRequest.getPassword())
        );

        User user = userRepository.findByEmail(signupRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        if (authentication.isAuthenticated()) {
            String jwt = jwtProvider.generateToken(user);
            return ResponseEntity.ok(new JWTAuthResponse(jwt, signupRequest.getEmail()));
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
