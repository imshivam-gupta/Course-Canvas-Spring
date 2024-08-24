package com.example.coursecanvasspring.controller;

import com.example.coursecanvasspring.entity.code.*;
import com.example.coursecanvasspring.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/code")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @PostMapping("/addCompany")
    public ResponseEntity<?> addCompany(@RequestBody Map<String,String> req){
        Company company = codeService.addCompany(req);
        return ResponseEntity.ok(company);
    }

    @PostMapping("/addTopic")
    public ResponseEntity<?> addTopic(@RequestBody Map<String,String> req){
        Topic topic = codeService.addTopic(req);
        return ResponseEntity.ok(topic);
    }

    @PostMapping("/addProblem")
    public ResponseEntity<?> addProblem(@RequestBody Map<String,String> req){
        Problem problem = codeService.addProblem(req);
        return ResponseEntity.ok(problem);
    }

    @PostMapping("/{problemId}/createSubmission")
    public ResponseEntity<?> createSubmission(@PathVariable String problemId, @RequestBody Map<String,String> req) throws IOException {
        Submission submission = codeService.createSubmission(problemId, req);
        return ResponseEntity.ok(submission);
    }

    @GetMapping("/{submissionId}/getSubmissionResult")
    public ResponseEntity<?> getSubmissionResult(@PathVariable String submissionId){
        Submission submission = codeService.getSubmissionResult(submissionId);
        return ResponseEntity.ok(submission);
    }
}
