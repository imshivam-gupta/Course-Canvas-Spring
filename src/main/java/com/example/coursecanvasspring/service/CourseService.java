package com.example.coursecanvasspring.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.coursecanvasspring.entity.course.Course;
import com.example.coursecanvasspring.entity.course.CourseCategory;
import com.example.coursecanvasspring.repository.course.CourseCategoryRepository;
import com.example.coursecanvasspring.repository.course.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static com.example.coursecanvasspring.constants.StringConstants.*;
import static com.example.coursecanvasspring.helper.RequestValidators.validateRequestKeys;
import static com.example.coursecanvasspring.helper.S3UploadHelper.uploadFile;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseCategoryRepository courseCategoryRepository;

    @Value("${aws.s3.bucket}")
    private String BUCKET_NAME;

    @Autowired
    private AmazonS3 amazonS3;

    public Course getCourse(String courseId){
        return courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Course createCourse(Map<String,String> reqBody){

        if(!validateRequestKeys(COURSE_CREATE_NOT_NULL_FIELDS, reqBody)){
            throw new RuntimeException("Invalid request body, missing required fields");
        }

        Course newCourse = new Course();
        newCourse.setTitle(reqBody.get(TITLE_COURSE_FIELD));
        newCourse.setDescription(reqBody.get(DESCRIPTION_COURSE_FIELD));
        newCourse.setPrice(Double.valueOf(reqBody.get(PRICE_COURSE_FIELD)));
        newCourse.setIsPublished(Boolean.valueOf(reqBody.get(PUBLISHED_COURSE_FIELD)));
        newCourse.setIsFree(Boolean.valueOf(reqBody.get(FREE_COURSE_FIELD)));
        newCourse.setDiscount(Double.valueOf(reqBody.get(DISCOUNT_COURSE_FIELD)));
        newCourse = courseRepository.save(newCourse);
        return newCourse;
    }

    public CourseCategory createCourseCategory(Map<String,String> reqBody){
        if(!validateRequestKeys(CATEGORY_CREATE_NOT_NULL_FIELDS, reqBody)){
            throw new RuntimeException("Invalid request body, missing required fields");
        }

        CourseCategory newCourseCategory = new CourseCategory();
        newCourseCategory.setName(reqBody.get(NAME_CATEGORY_FIELD));
        newCourseCategory.setDescription(reqBody.get(DESCRIPTION_CATEGORY_FIELD));
        newCourseCategory = courseCategoryRepository.save(newCourseCategory);
        return newCourseCategory;
    }

    public Course addBanner(MultipartFile file, String courseId) throws RuntimeException, IOException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        String bannerUrl = uploadFile(file, amazonS3, BUCKET_NAME);
        course.setBannerUrl(bannerUrl);
        course  = courseRepository.save(course);
        return course;
    }

}
