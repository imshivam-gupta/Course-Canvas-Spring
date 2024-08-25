package com.example.coursecanvasspring.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.coursecanvasspring.entity.chapter.Chapter;
import com.example.coursecanvasspring.entity.course.Course;
import com.example.coursecanvasspring.entity.course.CourseCategory;
import com.example.coursecanvasspring.entity.section.Section;
import com.example.coursecanvasspring.entity.user.User;
import com.example.coursecanvasspring.repository.course.CourseCategoryRepository;
import com.example.coursecanvasspring.repository.course.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Comparator;
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

    @Autowired
    private AuthorizationService authorizationService;

    public Course getCourse(String courseId){
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        course.getSections().sort(Comparator.comparingLong(Section::getPosition));
        course.getSections().forEach(section ->
                section.getChapters().sort(Comparator.comparingLong(Chapter::getPosition))
        );

        return course;
    }

    public Course createCourse(Map<String,String> reqBody){
        if(!validateRequestKeys(COURSE_CREATE_NOT_NULL_FIELDS, reqBody)){
            throw new RuntimeException("Invalid request body, missing required fields");
        }

        User loggedInUser = authorizationService.getLoggedInUser();

        CourseCategory courseCategory = courseCategoryRepository.findById(reqBody.get(CATEGORY_COURSE_FIELD))
                .orElseThrow(() -> new RuntimeException("Course category not found"));

        Course newCourse = new Course();
        newCourse.setTitle(reqBody.get(TITLE_COURSE_FIELD));
        newCourse.setDescription(reqBody.get(DESCRIPTION_COURSE_FIELD));
        newCourse.setPrice(Double.valueOf(reqBody.get(PRICE_COURSE_FIELD)));
        newCourse.setIsPublished(Boolean.valueOf(reqBody.get(PUBLISHED_COURSE_FIELD)));
        newCourse.setIsFree(Boolean.valueOf(reqBody.get(FREE_COURSE_FIELD)));
        newCourse.setDiscount(Double.valueOf(reqBody.get(DISCOUNT_COURSE_FIELD)));
        newCourse.setCategory(courseCategory);
        newCourse.setOwner(loggedInUser);
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

    private void copyNonNullProperties(Map<String,String> source, Course target) {
        if(source.get(TITLE_COURSE_FIELD) != null) target.setTitle(source.get(TITLE_COURSE_FIELD));
        if(source.get(DESCRIPTION_COURSE_FIELD) != null) target.setDescription(source.get(DESCRIPTION_COURSE_FIELD));
        if(source.get(PRICE_COURSE_FIELD) != null) target.setPrice(Double.valueOf(source.get(PRICE_COURSE_FIELD)));
        if(source.get(PUBLISHED_COURSE_FIELD) != null) target.setIsPublished(Boolean.valueOf(source.get(PUBLISHED_COURSE_FIELD)));
        if(source.get(FREE_COURSE_FIELD) != null) target.setIsFree(Boolean.valueOf(source.get(FREE_COURSE_FIELD)));
        if(source.get(DISCOUNT_COURSE_FIELD) != null) target.setDiscount(Double.valueOf(source.get(DISCOUNT_COURSE_FIELD)));

        if(source.get(CATEGORY_COURSE_FIELD) != null) {
            CourseCategory courseCategory = courseCategoryRepository.findById(source.get(CATEGORY_COURSE_FIELD))
                    .orElseThrow(() -> new RuntimeException("Course category not found"));
            target.setCategory(courseCategory);
        }

        if(source.get(BANNER_URL_COURSE_FIELD)!=null) target.setBannerUrl(source.get(BANNER_URL_COURSE_FIELD));

        User loggedInUser = authorizationService.getLoggedInUser();
        target.setOwner(loggedInUser);
    }

    public Course updateCourse(Map<String,String> courseUpdate, String courseId){
        Course existingcourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        copyNonNullProperties(courseUpdate, existingcourse);
        return courseRepository.save(existingcourse);
    }

    public Course publishCourse(String courseId){
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        for (String fieldName : COURSE_PUBLISH_NOT_NULL_FIELDS) {
            try {
                Field field = existingCourse.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(existingCourse);
                if (value == null) {
                    throw new RuntimeException("Course field: " + fieldName + " cannot be null");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Error accessing field: " + fieldName, e);
            }
        }

        // At least one section should be present and published
        if(existingCourse.getSections().isEmpty()){
            throw new RuntimeException("Course should have at least one section");
        }

        for (int i = 0; i < existingCourse.getSections().size(); i++) {
            if(existingCourse.getSections().get(i).getIsPublished()){
                break;
            }
            if(i == existingCourse.getSections().size()-1){
                throw new RuntimeException("Course should have at least one section published");
            }
        }

        existingCourse.setIsPublished(true);
        return courseRepository.save(existingCourse);
    }
}
