package com.example.coursecanvasspring.entity.chapter;

import com.example.coursecanvasspring.constants.StringConstants;
import com.example.coursecanvasspring.enums.ContentType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

import static com.example.coursecanvasspring.constants.StringConstants.CHAPTER_COLLECTION;

@Getter
@Setter
@Document(collection = CHAPTER_COLLECTION)
public class DocumentChapter extends Chapter implements Serializable {

    public DocumentChapter(){
        this.setContentType(StringConstants.CHAPTER_TYPE_DOCUMENT);
    }

    private String articleUrl;
}
