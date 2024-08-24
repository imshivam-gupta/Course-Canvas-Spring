package com.example.coursecanvasspring.controller;

import com.example.coursecanvasspring.entity.course.Course;
import com.example.coursecanvasspring.entity.course.CourseCategory;
import com.example.coursecanvasspring.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getChapter(@PathVariable String courseId) {
        Course course = courseService.getCourse(courseId);
        return ResponseEntity.ok(course);
    }

    @PostMapping("/creatCategory")
    public ResponseEntity<?> createCourseCategory(@RequestBody Map<String,String> courseCategory){
        CourseCategory createdCourseCategory = courseService.createCourseCategory(courseCategory);
        return ResponseEntity.ok(createdCourseCategory);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCourse(@RequestBody Map<String,String> course){
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.ok(createdCourse);
    }

    @PatchMapping("/{courseId}/banner")
    public ResponseEntity<?> updateCourseBanner(@RequestParam(value = "file", required = true) MultipartFile file, @PathVariable String courseId) throws IOException {
        Course updatedCourse = courseService.addBanner(file,courseId);
        return ResponseEntity.ok(updatedCourse);
    }
}
