package com.example.coursecanvasspring.enums;

import com.example.coursecanvasspring.helper.StringConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthProvider {
    LOCAL(StringConstants.LOCAL_STRATEGY),
    GOOGLE(StringConstants.GOOGLE_STRATEGY);

    private final String provider;
}

