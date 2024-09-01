package com.example.coursecanvasspring.service;

import com.example.coursecanvasspring.entity.chapter.Chapter;
import com.example.coursecanvasspring.entity.chapter.CodeChapter;
import com.example.coursecanvasspring.entity.chapter.DocumentChapter;
import com.example.coursecanvasspring.entity.chapter.VideoChapter;
import com.example.coursecanvasspring.entity.code.Problem;
import com.example.coursecanvasspring.entity.section.Section;
import com.example.coursecanvasspring.repository.chapter.ChapterRepository;
import com.example.coursecanvasspring.repository.chapter.DocumentRepository;
import com.example.coursecanvasspring.repository.chapter.VideoRepository;
import com.example.coursecanvasspring.repository.code.ProblemRepository;
import com.example.coursecanvasspring.repository.section.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;

import static com.example.coursecanvasspring.constants.StringConstants.*;
import static com.example.coursecanvasspring.helper.RequestValidators.validateRequestKeys;

@Service
public class ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private CodeService codeService;

    @Cacheable(value = "chapter", key = "#chapterId")
    public Chapter getChapter(String chapterId) {
        System.out.println("ChapterService.getChapter was called");
        Chapter chapter = chapterRepository.findById(chapterId).orElse(null);

        if (chapter == null) {
            return null;
        }

        return switch (chapter.getContentType()) {
            case CHAPTER_TYPE_DOCUMENT -> documentRepository.findById(chapterId).orElse(null);
            case CHAPTER_TYPE_VIDEO -> videoRepository.findById(chapterId).orElse(null);
            case CHAPTER_TYPE_CODE -> {
                CodeChapter codeChapter = (CodeChapter) chapter;
                Problem existingProblem = codeChapter.getProblem();
                existingProblem.setBoilerplateCodes(codeService.getBoilerplateCodes(existingProblem.getTitle()));
                yield codeChapter;
            }
            default -> chapter;
        };
        // Design Pattern Use: metadata storage
    }

    public Chapter createChapter(Map<String, String> chapter,String sectionId){
        Section section = sectionRepository.findById(sectionId).
                orElseThrow(() -> new RuntimeException("Section not found"));

        if(!validateRequestKeys(CHAPTER_CREATE_NOT_NULL_FIELDS, chapter)){
            throw new RuntimeException("Invalid request body, missing required fields");
        }

        Chapter newChapter = switch (chapter.get(CONTENT_TYPE_CHAPTER_FIELD)) {
            case CHAPTER_TYPE_DOCUMENT -> new DocumentChapter();
            case CHAPTER_TYPE_VIDEO -> new VideoChapter();
            case CHAPTER_TYPE_CODE -> new CodeChapter();
            default -> new Chapter();
        };

        newChapter.setTitle(chapter.get(TITLE_CHAPTER_FIELD));
        newChapter.setDescription(chapter.get(DESCRIPTION_CHAPTER_FIELD));
        newChapter.setPosition(getLastChapterPosition(sectionId));
        newChapter.setIsPublished(Boolean.parseBoolean(chapter.get(PUBLISHED_CHAPTER_FIELD)));
        newChapter.setIsFree(Boolean.parseBoolean(chapter.get(FREE_CHAPTER_FIELD)));

        if(newChapter instanceof DocumentChapter documentChapter){
            if(!validateRequestKeys(DOC_CHAPTER_CREATE_NOT_NULL_FIELDS, chapter)){
                throw new RuntimeException("Invalid request body, missing required fields");
            }

            documentChapter.setArticleUrl(chapter.get(ARTICLE_URL_CHAPTER_FIELD));
        } else if(newChapter instanceof VideoChapter videoChapter){
            if(!validateRequestKeys(VIDEO_CHAPTER_CREATE_NOT_NULL_FIELDS, chapter)){
                throw new RuntimeException("Invalid request body, missing required fields");
            }

            videoChapter.setVideoUrl(chapter.get(VIDEO_URL_CHAPTER_FIELD));
            videoChapter.setDuration(chapter.get(DURATION_CHAPTER_FIELD));
            videoChapter.setThumbnailUrl(chapter.get(THUMBNAIL_URL_CHAPTER_FIELD));
            videoChapter.setVideoType(chapter.get(VIDEO_TYPE_CHAPTER_FIELD));
            videoChapter.setVideoQuality(chapter.get(VIDEO_QUALITY_CHAPTER_FIELD));
            videoChapter.setVideoSize(chapter.get(VIDEO_SIZE_CHAPTER_FIELD));
        } else if(newChapter instanceof CodeChapter codeChapter){
            if(!validateRequestKeys(CODE_CHAPTER_CREATE_NOT_NULL_FIELDS, chapter)){
                throw new RuntimeException("Invalid request body, missing required fields");
            }

            Problem problem = problemRepository.findById(chapter.get(PROBLEM_ID_CHAPTER_FIELD)).
                    orElseThrow(() -> new RuntimeException("Problem not found"));

            codeChapter.setProblem(problem);
        }

        newChapter = chapterRepository.save(newChapter);

        section.getChapters().add(newChapter);
        sectionRepository.save(section);

        return newChapter;
    }

    private void copyNonNullProperties(Map<String,String> source, Chapter target) {
        if(source.get(TITLE_CHAPTER_FIELD) != null) target.setTitle(source.get(TITLE_CHAPTER_FIELD));
        if(source.get(DESCRIPTION_CHAPTER_FIELD) != null) target.setDescription(source.get(DESCRIPTION_CHAPTER_FIELD));
        if(source.get(PUBLISHED_CHAPTER_FIELD) != null) target.setIsPublished(Boolean.parseBoolean(source.get(PUBLISHED_CHAPTER_FIELD)));
        if(source.get(FREE_CHAPTER_FIELD) != null) target.setIsFree(Boolean.parseBoolean(source.get(FREE_CHAPTER_FIELD)));
    }

    public Chapter updateChapter(Map<String,String> chapterUpdate, String chapterId){
        Chapter chapter = chapterRepository.findById(chapterId).
                orElseThrow(() -> new RuntimeException("Chapter not found"));

        copyNonNullProperties(chapterUpdate, chapter);
        chapterRepository.save(chapter);
        return chapter;
    }

    public Chapter publishChapter(String chapterId){
        Chapter chapter = chapterRepository.findById(chapterId).
                orElseThrow(() -> new RuntimeException("Chapter not found"));

        if(chapter.getTitle() == null || chapter.getContentType() == null){
            throw new RuntimeException("Chapter title and content type cannot be null");
        }

        switch (chapter.getContentType()) {
            case CHAPTER_TYPE_DOCUMENT -> {
                for(String fieldName : DOC_CHAPTER_PUBLISH_NOT_NULL_FIELDS) {
                    try {
                        Field field = chapter.getClass().getDeclaredField(fieldName);
                        field.setAccessible(true);
                        Object value = field.get(chapter);
                        if (value == null) {
                            throw new RuntimeException("Document Chapter field: " + fieldName + " cannot be null");
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException("Document Chapter field: " + fieldName + " cannot be null");
                    }
                }
            }
            case CHAPTER_TYPE_VIDEO -> {
                for(String fieldName : VIDEO_CHAPTER_PUBLISH_NOT_NULL_FIELDS) {
                    try {
                        Field field = chapter.getClass().getDeclaredField(fieldName);
                        field.setAccessible(true);
                        Object value = field.get(chapter);
                        if (value == null) {
                            throw new RuntimeException("Video Chapter field: " + fieldName + " cannot be null");
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException("Video Chapter field: " + fieldName + " cannot be null");
                    }
                }
            }
        }

        chapter.setIsPublished(true);
        chapterRepository.save(chapter);
        return chapter;
    }

    public Chapter unpublishChapter(String chapterId,String sectionId){
        Chapter chapter = chapterRepository.findById(chapterId).
                orElseThrow(() -> new RuntimeException("Chapter not found"));

        chapter.setIsPublished(false);
        chapterRepository.save(chapter);

        Section section = sectionRepository.findById(sectionId).
                orElseThrow(() -> new RuntimeException("Section not found"));

        if(section.getIsPublished()){
            // check if no chapter in section is published then set section to unpublished
            boolean isSectionPublished = section.getChapters().stream().anyMatch(Chapter::getIsPublished);
            if(!isSectionPublished){
                section.setIsPublished(false);
                sectionRepository.save(section);
            }
        }

        return chapter;
    }

    public void deleteChapter(String chapterId,String sectionId){
        Chapter chapter = chapterRepository.findById(chapterId).
                orElseThrow(() -> new RuntimeException("Chapter not found"));

        chapterRepository.delete(chapter);

        Section section = sectionRepository.findById(sectionId).
                orElseThrow(() -> new RuntimeException("Section not found"));

        section.getChapters().remove(chapter);
        sectionRepository.save(section);
    }

    public Section reorderChapter(String sectionId,Map<String,Long> chapterOrder){
        Section section = sectionRepository.findById(sectionId).
                orElseThrow(() -> new RuntimeException("Section not found"));

        for(Map.Entry<String,Long> entry : chapterOrder.entrySet()){
            Chapter chapter = chapterRepository.findById(entry.getKey()).
                    orElseThrow(() -> new RuntimeException("Chapter not found"));

            chapter.setPosition(entry.getValue());
            chapterRepository.save(chapter);
        }

        section.getChapters().sort((c1,c2) -> (int) (c1.getPosition() - c2.getPosition()));
        return sectionRepository.save(section);
    }

    private Long getLastChapterPosition(String sectionId) throws RuntimeException{
        Section section = sectionRepository.findById(sectionId).
                orElseThrow(() -> new RuntimeException("Section not found"));

        return section.getChapters().size() + 1L;
    }

}
