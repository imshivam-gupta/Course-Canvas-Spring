package com.example.coursecanvasspring.controller;

import com.example.coursecanvasspring.entity.chapter.Chapter;
import com.example.coursecanvasspring.service.ChapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.coursecanvasspring.constants.StringConstants.*;

@RestController
@Slf4j
@RequestMapping
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
}
