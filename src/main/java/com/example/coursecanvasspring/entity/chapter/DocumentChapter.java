package com.example.coursecanvasspring.entity.chapter;

import com.example.coursecanvasspring.constants.StringConstants;
import com.example.coursecanvasspring.enums.ContentType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Chapter")
public class DocumentChapter extends Chapter{

    public DocumentChapter(){
        this.setContentType(StringConstants.CHAPTER_TYPE_DOCUMENT);
    }

    private String articleUrl;
}
