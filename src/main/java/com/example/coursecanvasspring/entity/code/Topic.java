package com.example.coursecanvasspring.entity.code;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

import static com.example.coursecanvasspring.constants.StringConstants.TOPIC_COLLECTION;

@Getter
@Setter
@Document(collection = TOPIC_COLLECTION)
public class Topic implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String _id;
    private String title;
    private String description;
}
