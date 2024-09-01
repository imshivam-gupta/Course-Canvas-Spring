package com.example.coursecanvasspring.entity.chapter;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.io.Serializable;

import static com.example.coursecanvasspring.constants.StringConstants.CHAPTER_COLLECTION;
import static com.example.coursecanvasspring.constants.StringConstants.CHAPTER_TYPE_ASSIGNMENT;

@Getter
@Setter
@Document(collection = CHAPTER_COLLECTION)
public class AssignmentChapter extends Chapter implements Serializable {

    public AssignmentChapter(){
        this.setContentType(CHAPTER_TYPE_ASSIGNMENT);
    }

    private String assignmentUrl;
}
