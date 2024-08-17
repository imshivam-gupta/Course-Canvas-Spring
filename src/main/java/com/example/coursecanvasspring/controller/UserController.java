package com.example.coursecanvasspring.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.example.coursecanvasspring.entity.user.User;
import com.example.coursecanvasspring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AmazonS3 amazonS3;

    @GetMapping("")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/getByEmail")
    public ResponseEntity<Object> getUserByEmail(@RequestBody Map<String, String> requestBody) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);

        String emailLogged = null;

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();
            emailLogged = (String) attributes.get("email");
        }

        String email = requestBody.get("email");

        if (emailLogged != null && !emailLogged.equals(email)) {
            return ResponseEntity.status(403).body("Unauthorized");
        }

        Optional<User> user = userService.findUserByEmail(email);
        log.info("User: " + user);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }


    private static final String BUCKET_NAME = "myaws3020";

    @PatchMapping("/{id}/uploadImage")
    public ResponseEntity<User> uploadImage(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            amazonS3.putObject(BUCKET_NAME, fileName, file.getInputStream(), null);
            String fileUrl = amazonS3.getUrl(BUCKET_NAME, fileName).toString();

            Optional<User> optionalUser = userService.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setProfilePicture(fileUrl);
                userService.updateUser(user);
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
