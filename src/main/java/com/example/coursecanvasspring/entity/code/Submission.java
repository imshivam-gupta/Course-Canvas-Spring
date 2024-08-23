package com.example.coursecanvasspring.entity.code;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Document(collection = "Submission")
public class Submission {
    private String _id;
    private String code;
    private String language;
    private String status;
    private List<String> tokens = new ArrayList<>();
}
