package com.example.coursecanvasspring.entity.chapter;

import com.example.coursecanvasspring.enums.ContentType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "chapters")
public class ArticleChapter extends Chapter{

    public ArticleChapter(){
        this.setContentType(ContentType.ARTICLE);
    }

    private String articleUrl;
}
