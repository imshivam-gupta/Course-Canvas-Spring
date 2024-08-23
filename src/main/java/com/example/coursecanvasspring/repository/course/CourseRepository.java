package com.example.coursecanvasspring.repository.course;

import com.example.coursecanvasspring.entity.course.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course,String> {
}
