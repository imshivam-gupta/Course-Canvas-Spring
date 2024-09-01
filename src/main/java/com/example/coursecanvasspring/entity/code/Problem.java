package com.example.coursecanvasspring.entity.code;

import com.example.coursecanvasspring.enums.Language;
import com.example.coursecanvasspring.enums.ProblemDifficulty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

import static com.example.coursecanvasspring.constants.StringConstants.PROBLEM_COLLECTION;

@Getter
@Setter
@Document(collection = PROBLEM_COLLECTION)
public class Problem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String _id;
    private String title;
    private String descriptionUrl;
    private ProblemDifficulty difficulty = ProblemDifficulty.MEDIUM;
    private List<String> hints = new ArrayList<>();
    private String editorialUrl;
    private List<Language> supportedLanguages = new ArrayList<>();
    private List<BoilerplateCode> boilerplateCodes = new ArrayList<>();

    @DBRef
    private List<Problem> relatedProblems = new ArrayList<>();

    @DBRef
    private List<Topic> topics = new ArrayList<>();

    @DBRef
    private List<Company> companies = new ArrayList<>();

    @DBRef
    private List<Submission> submissions = new ArrayList<>();
}
