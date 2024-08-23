package com.example.coursecanvasspring.entity.course;

import com.example.coursecanvasspring.entity.section.Section;
import com.example.coursecanvasspring.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

import static com.example.coursecanvasspring.constants.StringConstants.COURSE_COLLECTION;

@Getter
@Setter
@Document(collection = COURSE_COLLECTION)
public class Course {
    private String _id;
    private String title;
    private String description;
    private String bannerUrl;
    private Boolean isPublished = false;
    private Boolean isFree = false;
    private List<String> prerequisites;
    private List<String> tags;
    private Double price = 0.0;
    private Double discount = 0.0;

    @DBRef
    private CourseCategory category;

    @DBRef
    private List<Section> sections = new ArrayList<>();

    @DBRef
    private User owner;

    @DBRef
    private List<User> enrolledUsers = new ArrayList<>();
}
