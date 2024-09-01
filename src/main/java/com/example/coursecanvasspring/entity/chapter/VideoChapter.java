package com.example.coursecanvasspring.entity.chapter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

import static com.example.coursecanvasspring.constants.StringConstants.CHAPTER_COLLECTION;
import static com.example.coursecanvasspring.constants.StringConstants.CHAPTER_TYPE_VIDEO;

@Getter
@Setter
@Document(collection = CHAPTER_COLLECTION)
public class VideoChapter extends Chapter implements Serializable {

    public VideoChapter() {
        this.setContentType(CHAPTER_TYPE_VIDEO);
    }

    private String videoUrl;
    private String thumbnailUrl;
    private String duration;
    private String videoType;
    private String videoQuality;
    private String videoSize;
}