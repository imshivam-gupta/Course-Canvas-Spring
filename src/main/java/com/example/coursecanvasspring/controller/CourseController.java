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

import static com.example.coursecanvasspring.constants.StringConstants.*;

@RestController
@Slf4j
@RequestMapping
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping(GET_COURSE_ROUTE)
    public ResponseEntity<?> getChapter(@PathVariable String courseId) {
        Course course = courseService.getCourse(courseId);
        return ResponseEntity.ok(course);
    }

    @PostMapping(CREATE_CATEGORY_ROUTE)
    public ResponseEntity<?> createCourseCategory(@RequestBody Map<String,String> courseCategory){
        CourseCategory createdCourseCategory = courseService.createCourseCategory(courseCategory);
        return ResponseEntity.ok(createdCourseCategory);
    }

    @PostMapping(CREATE_COURSE_ROUTE)
    public ResponseEntity<?> createCourse(@RequestBody Map<String,String> course){
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.ok(createdCourse);
    }

    @PatchMapping(BANNER_COURSE_ROUTE)
    public ResponseEntity<?> updateCourseBanner(@RequestParam(value = "file") MultipartFile file, @PathVariable String courseId) throws IOException {
        Course updatedCourse = courseService.addBanner(file,courseId);
        return ResponseEntity.ok(updatedCourse);
    }

    @PatchMapping(UPDATE_COURSE_ROUTE)
    public ResponseEntity<?> updateCourse(@RequestBody Map<String,String> courseUpdate, @PathVariable String courseId){
        Course updatedCourse = courseService.updateCourse(courseUpdate,courseId);
        return ResponseEntity.ok(updatedCourse);
    }

    @PatchMapping(PUBLISH_COURSE_ROUTE)
    public ResponseEntity<?> publishCourse(@PathVariable String courseId){
        Course updatedCourse = courseService.publishCourse(courseId);
        return ResponseEntity.ok(updatedCourse);
    }
}
