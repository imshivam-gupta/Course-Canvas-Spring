package com.example.coursecanvasspring.entity.section;

import com.example.coursecanvasspring.entity.chapter.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.coursecanvasspring.constants.StringConstants.SECTION_COLLECTION;

@Getter
@Setter
@Document(collection = SECTION_COLLECTION)
public class Section {
    private String _id;
    private String title;
    private String description;
    private String bannerUrl;
    private Long position;
    private Boolean isPublished = false;
    private Boolean isFree = false;

    @DBRef
    private List<Chapter> chapters = new ArrayList<>();
}
