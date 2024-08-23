package com.example.coursecanvasspring.entity.chapter;

import com.example.coursecanvasspring.constants.StringConstants;
import com.example.coursecanvasspring.entity.code.Problem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.example.coursecanvasspring.constants.StringConstants.CHAPTER_COLLECTION;

@Getter
@Setter
@Document(collection = CHAPTER_COLLECTION)
public class CodeChapter extends Chapter {
    public CodeChapter(){
        this.setContentType(StringConstants.CHAPTER_TYPE_CODE);
    }

    @DBRef
    private Problem problem;
}
