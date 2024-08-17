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
public class Teacher extends User{
    public Teacher() {
        this.setRole(UserRole.INSTRUCTOR);
    }

    private String bio;
    private String website;
    private String youtube;
    private String twitter;
    private String linkedin;

    private List<String> curatedCourse = new ArrayList<String>();
    private List<String> publishedCourse = new ArrayList<String>();
    private List<String> drafts = new ArrayList<String>();
}
