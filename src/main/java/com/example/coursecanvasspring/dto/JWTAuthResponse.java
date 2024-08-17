package com.example.coursecanvasspring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JWTAuthResponse {
    private String token;
    private String email;
}
