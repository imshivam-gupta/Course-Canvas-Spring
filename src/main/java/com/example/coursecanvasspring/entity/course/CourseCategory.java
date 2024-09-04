package com.example.coursecanvasspring.entity.course;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

import static com.example.coursecanvasspring.constants.StringConstants.CATEGORY_COLLECTION;

@Getter
@Setter
@Document(collection = CATEGORY_COLLECTION)
public class CourseCategory implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String _id;
    private String name;
    private String description;
}
