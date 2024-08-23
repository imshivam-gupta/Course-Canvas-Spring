package com.example.coursecanvasspring.entity.user;

import com.example.coursecanvasspring.constants.StringConstants;


public class UserFactory {
    public static User createUser(String userRole) {
        if (userRole == null) {
            return null;
        } else if (userRole.equalsIgnoreCase(StringConstants.STUDENT_ROLE)) {
            return new Student();
        } else if (userRole.equalsIgnoreCase(StringConstants.INSTRUCTOR_ROLE)) {
            return new Teacher();
        } else {
            return null;
        }
    }
}
