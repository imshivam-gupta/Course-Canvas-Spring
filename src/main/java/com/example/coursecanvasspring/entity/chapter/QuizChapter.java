package com.example.coursecanvasspring.entity.chapter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.example.coursecanvasspring.constants.StringConstants.CHAPTER_COLLECTION;
import static com.example.coursecanvasspring.constants.StringConstants.CHAPTER_TYPE_QUIZ;

@Getter
@Setter
@Document(collection = CHAPTER_COLLECTION)
public class QuizChapter extends Chapter{

    public QuizChapter(){
        this.setContentType(CHAPTER_TYPE_QUIZ);
    }

    // TODO: Add quiz related fields
}
