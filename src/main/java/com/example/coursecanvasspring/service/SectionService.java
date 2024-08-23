package com.example.coursecanvasspring.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.coursecanvasspring.entity.course.Course;
import com.example.coursecanvasspring.entity.section.Section;
import com.example.coursecanvasspring.repository.course.CourseRepository;
import com.example.coursecanvasspring.repository.section.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static com.example.coursecanvasspring.constants.StringConstants.*;
import static com.example.coursecanvasspring.helper.RequestValidators.validateRequestKeys;
import static com.example.coursecanvasspring.helper.S3UploadHelper.uploadFile;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Value("${aws.s3.bucket}")
    private String BUCKET_NAME;

    @Autowired
    private AmazonS3 amazonS3;


    public Section getSection(String sectionId) throws RuntimeException{
        return sectionRepository.findById(sectionId).
                orElseThrow(() -> new RuntimeException("Section not found"));
    }

    public Section createSection(Map<String,String> reqBody, String courseId){
        if(!validateRequestKeys(SECTION_CREATE_NOT_NULL_FIELDS, reqBody)){
            return null;
        }

        Section newSection = new Section();
        newSection.setTitle(reqBody.get(TITLE_SECTION_FIELD));
        newSection.setDescription(reqBody.get(DESCRIPTION_SECTION_FIELD));
        newSection.setPosition(getLastSectionPosition(courseId));
        newSection.setIsPublished(Boolean.valueOf(reqBody.get(PUBLISHED_SECTION_FIELD)));
        newSection.setIsFree(Boolean.valueOf(reqBody.get(FREE_SECTION_FIELD)));
        newSection = sectionRepository.save(newSection);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.getSections().add(newSection);
        courseRepository.save(course);

        return newSection;
    }

    public Section addBanner(MultipartFile file, String sectionId) throws RuntimeException, IOException {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Section not found"));

        String bannerUrl = uploadFile(file, amazonS3, BUCKET_NAME);
        section.setBannerUrl(bannerUrl);
        sectionRepository.save(section);
        return section;
    }

    private Long getLastSectionPosition(String courseId) throws RuntimeException{
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return course.getSections().size() + 1L;
    }
}
