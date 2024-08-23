package com.example.coursecanvasspring.service;

import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public Boolean isCourseOwner(String courseId, String userId) {
        return true;
    }

    public String getLoggedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
