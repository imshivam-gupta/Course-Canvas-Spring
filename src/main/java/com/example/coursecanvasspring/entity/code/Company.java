package com.example.coursecanvasspring.entity.code;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

import static com.example.coursecanvasspring.constants.StringConstants.COMPANY_COLLECTION;

@Getter
@Setter
@Document(collection = COMPANY_COLLECTION)
public class Company {
    private String _id;
    private String name;
}
