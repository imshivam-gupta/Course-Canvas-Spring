package com.example.coursecanvasspring.entity.code;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Topic")
public class Topic {
    private String _id;
    private String title;
    private String description;
}
