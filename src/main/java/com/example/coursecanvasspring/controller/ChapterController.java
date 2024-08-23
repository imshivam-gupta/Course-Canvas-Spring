package com.example.coursecanvasspring.controller;

import com.example.coursecanvasspring.entity.chapter.Chapter;
import com.example.coursecanvasspring.service.ChapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @GetMapping("/{chapterId}")
    public ResponseEntity<?> getChapter(@PathVariable String chapterId) {
        Chapter chapter = chapterService.getChapter(chapterId);
        return ResponseEntity.ok(chapter);
    }

    @PostMapping("{sectionId}/create")
    public ResponseEntity<?> createChapter(@RequestBody Map<String,String> chapter,@PathVariable String sectionId){
        Chapter createdChapter = chapterService.createChapter(chapter,sectionId);
        return ResponseEntity.ok(createdChapter);
    }
}
