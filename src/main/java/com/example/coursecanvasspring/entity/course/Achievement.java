package com.example.coursecanvasspring.entity.course;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Achievement {
    private String name;
    private String description;
    private LocalDateTime earnedDate;
}
