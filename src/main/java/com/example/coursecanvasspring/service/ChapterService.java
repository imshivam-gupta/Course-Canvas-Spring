package com.example.coursecanvasspring.service;

import com.example.coursecanvasspring.entity.chapter.Chapter;
import com.example.coursecanvasspring.entity.chapter.DocumentChapter;
import com.example.coursecanvasspring.entity.chapter.VideoChapter;
import com.example.coursecanvasspring.entity.section.Section;
import com.example.coursecanvasspring.repository.chapter.ChapterRepository;
import com.example.coursecanvasspring.repository.chapter.DocumentRepository;
import com.example.coursecanvasspring.repository.chapter.VideoRepository;
import com.example.coursecanvasspring.repository.section.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Chapter getChapter(String chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElse(null);

        if (chapter == null) {
            return null;
        }

        return switch (chapter.getContentType()) {
            case CHAPTER_TYPE_DOCUMENT -> documentRepository.findById(chapterId).orElse(null);
            case CHAPTER_TYPE_VIDEO -> videoRepository.findById(chapterId).orElse(null);
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
        }

        newChapter = chapterRepository.save(newChapter);

        section.getChapters().add(newChapter);
        sectionRepository.save(section);

        return newChapter;
    }

    private Long getLastChapterPosition(String sectionId) throws RuntimeException{
        Section section = sectionRepository.findById(sectionId).
                orElseThrow(() -> new RuntimeException("Section not found"));

        return section.getChapters().size() + 1L;
    }

}
