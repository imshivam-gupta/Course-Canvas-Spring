package com.example.coursecanvasspring.entity.user;

import com.example.coursecanvasspring.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.example.coursecanvasspring.constants.StringConstants.USER_COLLECTION;

@Getter
@Setter
@Document(collection = USER_COLLECTION)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails, Serializable {
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

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.getRole() == null) {
            return Collections.emptyList();
        } else if (this.getRole().equals(UserRole.ADMIN)) {
            return List.of(new SimpleGrantedAuthority(UserRole.ADMIN.name()), new SimpleGrantedAuthority(UserRole.INSTRUCTOR.name()), new SimpleGrantedAuthority(UserRole.STUDENT.name()));
        } else if (this.getRole().equals(UserRole.INSTRUCTOR)) {
            return List.of(new SimpleGrantedAuthority(UserRole.INSTRUCTOR.name()), new SimpleGrantedAuthority(UserRole.STUDENT.name()));
        } else{
            return List.of(new SimpleGrantedAuthority(UserRole.STUDENT.name()));
        }
//        return Collections.singletonList(new SimpleGrantedAuthority(this.getRole().name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}