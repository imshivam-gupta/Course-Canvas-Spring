package com.example.coursecanvasspring.entity.course;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.example.coursecanvasspring.constants.StringConstants.CATEGORY_COLLECTION;

@Getter
@Setter
@Document(collection = CATEGORY_COLLECTION)
public class CourseCategory {
    private String _id;
    private String name;
    private String description;
}
