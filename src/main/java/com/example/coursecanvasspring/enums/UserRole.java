package com.example.coursecanvasspring.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.coursecanvasspring.constants.StringConstants.*;

@Getter
@AllArgsConstructor
public enum UserRole {
    ADMIN(ADMIN_ROLE),
    INSTRUCTOR(INSTRUCTOR_ROLE),
    STUDENT(STUDENT_ROLE),
    ASSISTANT(ASSISTANT_ROLE);

    private final String role;
}
