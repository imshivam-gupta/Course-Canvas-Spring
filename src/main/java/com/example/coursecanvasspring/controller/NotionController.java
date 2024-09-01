package com.example.coursecanvasspring.controller;

import com.example.coursecanvasspring.service.NotionService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.coursecanvasspring.constants.StringConstants.GET_NOTION_PAGE_ROUTE;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class NotionController {

    private final NotionService notionService;

    public NotionController(NotionService notionService) {
        this.notionService = notionService;
    }

    @PostMapping(GET_NOTION_PAGE_ROUTE)
    public Map<String, Object> getPage(@RequestBody Map<String, Object> request) {
        String pageId = (String) request.get("pageId");
        Map<String, Object> options = (Map<String, Object>) request.get("options");
        return notionService.getPage(pageId, options);
    }
}
