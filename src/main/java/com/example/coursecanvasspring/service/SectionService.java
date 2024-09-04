package com.example.coursecanvasspring.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.coursecanvasspring.entity.course.Course;
import com.example.coursecanvasspring.entity.section.Section;
import com.example.coursecanvasspring.repository.course.CourseRepository;
import com.example.coursecanvasspring.repository.section.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

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

    @Cacheable(value = "sections", key = "#sectionId")
    public Section getSection(String sectionId) throws RuntimeException{
        Section existing = sectionRepository.findById(sectionId).
                orElseThrow(() -> new RuntimeException("Section not found"));
        existing.getChapters().sort((c1,c2) -> (int) (c1.getPosition() - c2.getPosition()));
        return existing;
    }


    public Section createSection(Map<String,String> reqBody, String courseId){
        if(!validateRequestKeys(SECTION_CREATE_NOT_NULL_FIELDS, reqBody)){
            return null;
        }

        Section newSection = new Section();
        newSection.setNumOfDaysToComplete(Long.parseLong(reqBody.get(DAYS_TO_COMPLETE_SECTION_FIELD)));
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

    private void copyNonNullProperties(Map<String,String> source, Section target) {
        if(source.get(TITLE_SECTION_FIELD) != null) target.setTitle(source.get(TITLE_SECTION_FIELD));
        if(source.get(DESCRIPTION_SECTION_FIELD) != null) target.setDescription(source.get(DESCRIPTION_SECTION_FIELD));
        if(source.get(PUBLISHED_SECTION_FIELD) != null) target.setIsPublished(Boolean.valueOf(source.get(PUBLISHED_SECTION_FIELD)));
        if(source.get(FREE_SECTION_FIELD) != null) target.setIsFree(Boolean.valueOf(source.get(FREE_SECTION_FIELD)));
    }

    @CachePut(value = "sections", key = "#sectionId")
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

    @CachePut(value = "sections", key = "#sectionId")
    public Section updateSection(Map<String,String> sectionUpdate, String sectionId){
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Section not found"));

        copyNonNullProperties(sectionUpdate, section);
        sectionRepository.save(section);
        return section;
    }

    @CachePut(value = "sections", key = "#sectionId")
    public Section publishSection(String sectionId){
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Section not found"));

        for(String fieldName : SECTION_PUBLISH_NOT_NULL_FIELDS) {
            try {
                Field field = section.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(section);
                if (value == null) {
                    throw new RuntimeException("Section field: " + fieldName + " cannot be null");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Section field: " + fieldName + " cannot be null");
            }
        }

        if(section.getChapters().isEmpty()){
            throw new RuntimeException("Section should have at least one chapter");
        }

        for(int i=0; i<section.getChapters().size(); i++){
            if(section.getChapters().get(i).getIsPublished()){
                break;
            }
            if(i == section.getChapters().size()-1){
                throw new RuntimeException("Section should have at least one published chapter");
            }
        }

        section.setIsPublished(true);
        sectionRepository.save(section);
        return section;
    }

    @CachePut(value = "sections", key = "#sectionId")
    public Section unpublishSection(String sectionId, String courseId){
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Section not found"));

        section.setIsPublished(false);
        sectionRepository.save(section);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if(course.getIsPublished()){
            boolean isCoursePublished = course.getSections().stream().anyMatch(Section::getIsPublished);
            if(!isCoursePublished){
                course.setIsPublished(false);
                courseRepository.save(course);
            }
        }

        return section;
    }


    public void deleteSection(String sectionId, String courseId){
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Section not found"));

        sectionRepository.delete(section);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.getSections().remove(section);
        courseRepository.save(course);
    }

    public Course reorderSection(String courseId, Map<String,Long> sectionOrder){
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        for(Map.Entry<String,Long> entry : sectionOrder.entrySet()){
            Section section = sectionRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Section not found"));
            section.setPosition(entry.getValue());
            sectionRepository.save(section);
        }

        course.getSections().sort((s1,s2) -> (int) (s1.getPosition() - s2.getPosition()));
        return courseRepository.save(course);
    }
}
