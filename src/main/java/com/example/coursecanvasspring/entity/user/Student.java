package com.example.coursecanvasspring.entity.user;

import com.example.coursecanvasspring.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "users")
public class Student extends User{

    public Student() {
        this.setRole(UserRole.STUDENT);
    }

    private List<String> completedCourses = new ArrayList<>();
    private List<String> enrolledCourses = new ArrayList<>();
    private List<String> wishlist = new ArrayList<>();
    private List<String> cart = new ArrayList<>();
    private List<String> bookmarks = new ArrayList<>();
    private List<String> notes = new ArrayList<>();
}
