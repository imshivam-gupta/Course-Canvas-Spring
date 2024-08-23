package com.example.coursecanvasspring.entity.chapter;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;
import static com.example.coursecanvasspring.constants.StringConstants.CHAPTER_TYPE_ASSIGNMENT;

@Getter
@Setter
@Document(collection = "Chapter")
public class AssignmentChapter extends Chapter{

    public AssignmentChapter(){
        this.setContentType(CHAPTER_TYPE_ASSIGNMENT);
    }

    private String assignmentUrl;
}
