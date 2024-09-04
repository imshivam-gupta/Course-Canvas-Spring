package com.example.coursecanvasspring.entity.user;

import com.example.coursecanvasspring.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.coursecanvasspring.constants.StringConstants.USER_COLLECTION;

@Getter
@Setter
@Document(collection = USER_COLLECTION)
public class Teacher extends User implements Serializable {
    public Teacher() {
        this.setRole(UserRole.INSTRUCTOR);
    }

    private String bio;

    private List<String> curatedCourse = new ArrayList<String>();
    private List<String> publishedCourse = new ArrayList<String>();
    private List<String> drafts = new ArrayList<String>();
}
