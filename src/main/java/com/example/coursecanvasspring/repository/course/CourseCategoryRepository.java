package com.example.coursecanvasspring.repository.course;

import com.example.coursecanvasspring.entity.course.CourseCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseCategoryRepository extends MongoRepository<CourseCategory,String> {
}
