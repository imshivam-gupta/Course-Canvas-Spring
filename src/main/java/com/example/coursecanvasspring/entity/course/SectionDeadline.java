package com.example.coursecanvasspring.entity.course;

import com.example.coursecanvasspring.entity.section.Section;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SectionDeadline {
    private Section section;
    private LocalDateTime deadline;
    private Boolean isDeadlineExtended;
    private LocalDateTime extendedDeadline;
    private Boolean isMissed;
    private Double progress;
    private Boolean isCompleted;
}
