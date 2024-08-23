package com.example.coursecanvasspring.entity.user;

import com.example.coursecanvasspring.entity.course.Course;
import com.example.coursecanvasspring.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

import static com.example.coursecanvasspring.constants.StringConstants.USER_COLLECTION;

@Getter
@Setter
@Document(collection = USER_COLLECTION)
public class Student extends User{
    public Student() {
        this.setRole(UserRole.STUDENT);
    }

    private List<String> bookmarks = new ArrayList<>();
    private List<String> notes = new ArrayList<>();
    private Long rating;

    @DBRef
    private List<Course> completedCourses = new ArrayList<>();

    @DBRef
    private List<Course> enrolledCourses = new ArrayList<>();

    @DBRef
    private List<Course> wishlist = new ArrayList<>();

    @DBRef
    private List<Course> cart = new ArrayList<>();
}
