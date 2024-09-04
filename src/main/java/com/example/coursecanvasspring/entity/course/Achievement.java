package com.example.coursecanvasspring.entity.course;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class Achievement implements Serializable {
    private String name;
    private String description;
    private LocalDateTime earnedDate;
}
