package com.example.coursecanvasspring.entity.code;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class BoilerplateCode implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String code;
    private String language;
}
