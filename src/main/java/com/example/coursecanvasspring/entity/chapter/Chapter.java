package com.example.coursecanvasspring.entity.chapter;

import com.example.coursecanvasspring.enums.ContentType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

import static com.example.coursecanvasspring.constants.StringConstants.CHAPTER_COLLECTION;

@Getter
@Setter
@Document(collection = CHAPTER_COLLECTION)
public class Chapter implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String _id;
    private String title;
    private String description;
    private Long position;
    private Boolean isPublished = false;
    private Boolean isFree = false;
    private String contentType;
}
