package com.example.coursecanvasspring.service;

import com.example.coursecanvasspring.constants.StringConstants;
import com.example.coursecanvasspring.dto.JWTAuthResponse;
import com.example.coursecanvasspring.dto.LoginRequest;
import com.example.coursecanvasspring.dto.SignupRequest;
import com.example.coursecanvasspring.entity.user.Student;
import com.example.coursecanvasspring.entity.user.User;
import com.example.coursecanvasspring.entity.user.UserFactory;
import com.example.coursecanvasspring.enums.UserRole;
import com.example.coursecanvasspring.repository.user.StudentRepository;
import com.example.coursecanvasspring.repository.user.UserRepository;
import com.example.coursecanvasspring.security.local.JWTProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTProvider jwtProvider;

    public JWTAuthResponse registerUser(SignupRequest signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already taken!");
        }

        Student newUser = (Student) UserFactory.createUser(StringConstants.STUDENT_ROLE);
        newUser.setEmail(signupRequest.getEmail());
        newUser.setName(signupRequest.getName());
        newUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        newUser.setRole(UserRole.STUDENT);
        newUser.setEmailVerified(false);

        studentRepository.save(newUser);

        authenticateUser(signupRequest.getEmail(), signupRequest.getPassword());

        String jwt = jwtProvider.generateToken(newUser);
        return new JWTAuthResponse(jwt, newUser.getEmail());
    }

    public JWTAuthResponse authenticateUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("Invalid password!");
        }

        String jwt = jwtProvider.generateToken(user);
        return new JWTAuthResponse(jwt, loginRequest.getEmail());
    }

    private void authenticateUser(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }

    }
}
