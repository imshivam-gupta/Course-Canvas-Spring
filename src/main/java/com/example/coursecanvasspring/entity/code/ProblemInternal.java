package com.example.coursecanvasspring.entity.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemInternal implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String problemName;
    private String fullBoilerPlate;
    private List<String> inputs;
    private List<String> outputs;
}
