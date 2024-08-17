package com.example.coursecanvasspring.entity.chapter;

import com.example.coursecanvasspring.enums.ContentType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "chapters")
public class Chapter {
    private String title;
    private String description;
    private Long position;
    private Boolean isPublished;
    private Boolean isFree;
    private ContentType contentType;
}
