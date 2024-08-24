package com.example.coursecanvasspring.controller;

import com.example.coursecanvasspring.entity.section.Section;
import com.example.coursecanvasspring.service.SectionService;
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
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @GetMapping(GET_SECTION_ROUTE)
    public ResponseEntity<?> getSection(@PathVariable String sectionId) {
        Section section = sectionService.getSection(sectionId);
        return ResponseEntity.ok(section);
    }

    @PostMapping(CREATE_SECTION_ROUTE)
    public ResponseEntity<?> createSection(@RequestBody Map<String,String> section,@PathVariable String courseId){
        Section createdSection = sectionService.createSection(section,courseId);
        return ResponseEntity.ok(createdSection);
    }

    @PatchMapping(BANNER_SECTION_ROUTE)
    public ResponseEntity<?> updateSectionBanner(@RequestParam(value = "file") MultipartFile file, @PathVariable String sectionId) throws IOException {
        Section updatedSection = sectionService.addBanner(file,sectionId);
        return ResponseEntity.ok(updatedSection);
    }
}
