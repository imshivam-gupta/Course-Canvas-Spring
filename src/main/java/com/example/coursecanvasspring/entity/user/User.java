package com.example.coursecanvasspring.entity.user;

import com.example.coursecanvasspring.enums.UserRole;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.example.coursecanvasspring.constants.StringConstants.USER_COLLECTION;

@Getter
@Setter
@Document(collection = USER_COLLECTION)
public class User implements UserDetails {
    private String _id;
    private String name;
    private String password;
    private String profilePicture;
    private UserRole role;
    private Boolean emailVerified;
    private String providerId;
    private String oauthProvider;

    @CreatedDate
    private String createdAt;

    @Indexed(unique = true)
    private String email;

    private List<String> reviews = new ArrayList<>();
    private List<String> ratings = new ArrayList<>();
    private List<String> discussions = new ArrayList<>();

    private String website;
    private String youtube;
    private String twitter;
    private String linkedin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.getRole().name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}