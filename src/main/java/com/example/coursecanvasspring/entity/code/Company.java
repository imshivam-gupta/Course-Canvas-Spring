package com.example.coursecanvasspring.entity.code;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

@Getter
@Setter
@Document(collection = "Company")
public class Company {
    private String _id;
    private String name;
}
