package com.example.coursecanvasspring.entity.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProblemInternal {
    private String problemName;
    private String fullBoilerPlate;
    private List<String> inputs;
    private List<String> outputs;
}
