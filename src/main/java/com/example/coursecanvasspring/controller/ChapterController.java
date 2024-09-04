package com.example.coursecanvasspring.controller;

import com.example.coursecanvasspring.entity.chapter.Chapter;
import com.example.coursecanvasspring.entity.section.Section;
import com.example.coursecanvasspring.service.ChapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.coursecanvasspring.constants.StringConstants.*;

@RestController
@Slf4j
@RequestMapping
@CrossOrigin(origins = "http://localhost:5173")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @GetMapping(GET_CHAPTER_ROUTE)
    public ResponseEntity<?> getChapter(@PathVariable String chapterId) {
        Chapter chapter = chapterService.getChapter(chapterId);
        return ResponseEntity.ok(chapter);
    }

    @PostMapping(CREATE_CHAPTER_ROUTE)
    public ResponseEntity<?> createChapter(@RequestBody Map<String,String> chapter,@PathVariable String sectionId){
        Chapter createdChapter = chapterService.createChapter(chapter,sectionId);
        return ResponseEntity.ok(createdChapter);
    }

    @PatchMapping(UPDATE_CHAPTER_ROUTE)
    public ResponseEntity<?> updateChapter(@RequestBody Map<String,String> chapterUpdate, @PathVariable String chapterId){
        Chapter updatedChapter = chapterService.updateChapter(chapterUpdate,chapterId);
        return ResponseEntity.ok(updatedChapter);
    }

    @PatchMapping(PUBLISH_CHAPTER_ROUTE)
    public ResponseEntity<?> publishChapter(@PathVariable String chapterId){
        Chapter publishedChapter = chapterService.publishChapter(chapterId);
        return ResponseEntity.ok(publishedChapter);
    }

    @DeleteMapping(DELETE_CHAPTER_ROUTE)
    public ResponseEntity<?> deleteChapter(@PathVariable String chapterId, @PathVariable String sectionId){
        chapterService.deleteChapter(chapterId,sectionId);
        return ResponseEntity.ok("Chapter deleted successfully");
    }

    @PatchMapping(UNPUBLISH_CHAPTER_ROUTE)
    public ResponseEntity<?> unpublishChapter(@PathVariable String chapterId, @PathVariable String sectionId){
        Chapter unpublishedChapter = chapterService.unpublishChapter(chapterId,sectionId);
        return ResponseEntity.ok(unpublishedChapter);
    }

    @PatchMapping(REORDER_CHAPTER_ROUTE)
    public ResponseEntity<?> reorderChapter(@RequestBody Map<String,Long> chapterOrder, @PathVariable String sectionId){
        Section reorderedSection = chapterService.reorderChapter(sectionId, chapterOrder);
        return ResponseEntity.ok(reorderedSection);
    }

}
