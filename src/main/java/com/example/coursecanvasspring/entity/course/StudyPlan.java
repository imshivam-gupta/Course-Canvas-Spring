package com.example.coursecanvasspring.entity.course;

import com.example.coursecanvasspring.entity.section.Section;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class StudyPlan {
    private List<Section> plannedSections;
    private String schedule;
    private String notes;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
