package com.example.coursecanvasspring.enums;

import com.example.coursecanvasspring.helper.StringConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    ADMIN(StringConstants.ADMIN_ROLE),
    INSTRUCTOR(StringConstants.INSTRUCTOR_ROLE),
    STUDENT(StringConstants.STUDENT_ROLE),
    ASSISTANT(StringConstants.ASSISTANT_ROLE);

    private final String role;
}
