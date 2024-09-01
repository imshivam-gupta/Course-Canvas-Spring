package com.example.coursecanvasspring.entity.code;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.io.Serial;
import java.io.Serializable;

import static com.example.coursecanvasspring.constants.StringConstants.COMPANY_COLLECTION;

@Getter
@Setter
@Document(collection = COMPANY_COLLECTION)
public class Company implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String _id;
    private String name;
}
