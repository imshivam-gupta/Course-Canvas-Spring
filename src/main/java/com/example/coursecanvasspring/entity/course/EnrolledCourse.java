package com.example.coursecanvasspring.entity.course;

import com.example.coursecanvasspring.entity.chapter.Chapter;
import com.example.coursecanvasspring.entity.section.Section;
import com.example.coursecanvasspring.entity.user.Student;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.coursecanvasspring.constants.StringConstants.ENROLLED_COURSE_COLLECTION;

@Getter
@Setter
@Document(collection = ENROLLED_COURSE_COLLECTION)
public class EnrolledCourse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String _id;

    @DBRef
    private Course course;

    private Double progress = 0.0;
    private Double rating;
    private Boolean isPaused = false;
    private Boolean isCompleted = false;

    private List<Chapter> bookmarkedChapters;
    private List<Chapter> completedChapters;

    private List<SectionDeadline> sectionDeadlines;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @CreatedDate
    private LocalDateTime startDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastAccessed;
    private Chapter lastAccessedChapter;

    private StudyPlan studyPlan;

    private List<Achievement> achievements;

    private String learningStyle;
    private String preferredLearningPace;

    private Boolean remindersEnabled;
    private String reminderFrequency;
    private String reminderTime;

    private List<Section> unlockedSections;
    private List<Student> mentors;
}
