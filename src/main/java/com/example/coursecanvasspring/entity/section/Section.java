package com.example.coursecanvasspring.entity.section;

import com.example.coursecanvasspring.entity.chapter.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.coursecanvasspring.constants.StringConstants.SECTION_COLLECTION;

@Getter
@Setter
@Document(collection = SECTION_COLLECTION)
public class Section  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String _id;
    private String title;
    private String description;
    private String bannerUrl;
    private Long position;
    private Boolean isPublished = false;
    private Boolean isFree = false;
    private Long numOfDaysToComplete = 0L;

    @DBRef
    private List<Chapter> chapters = new ArrayList<>();
}
