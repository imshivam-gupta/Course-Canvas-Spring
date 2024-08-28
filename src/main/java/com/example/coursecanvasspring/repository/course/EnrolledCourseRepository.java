package com.example.coursecanvasspring.repository.course;

import com.example.coursecanvasspring.entity.course.EnrolledCourse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnrolledCourseRepository extends MongoRepository<EnrolledCourse, String> {
}