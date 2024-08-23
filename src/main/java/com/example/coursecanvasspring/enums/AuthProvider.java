package com.example.coursecanvasspring.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import static com.example.coursecanvasspring.constants.StringConstants.*;

@Getter
@AllArgsConstructor
public enum AuthProvider {
    LOCAL(LOCAL_STRATEGY),
    GOOGLE(GOOGLE_STRATEGY);

    private final String provider;
}

