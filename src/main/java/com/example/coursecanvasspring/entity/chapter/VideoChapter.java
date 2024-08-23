package com.example.coursecanvasspring.entity.chapter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Chapter")
public class VideoChapter extends Chapter {

    public VideoChapter() {
        this.setContentType("VIDEO");
    }

    private String videoUrl;
    private String thumbnailUrl;
    private String duration;
    private String videoType;
    private String videoQuality;
    private String videoSize;
}