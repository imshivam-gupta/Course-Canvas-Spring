package com.example.coursecanvasspring.entity.code;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.coursecanvasspring.constants.StringConstants.*;

@Getter
@Setter
@Document(collection = SUBMISSION_COLLECTION)
public class Submission {
    private String _id;
    private String code;
    private String language;
    private String status = SUBMISSION_STATUS_PENDING;
    private String token;
    private List<String> tokens = new ArrayList<>();
    private String failedTestCaseDetails;
}
