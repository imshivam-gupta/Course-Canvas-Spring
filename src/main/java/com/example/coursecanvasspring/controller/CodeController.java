package com.example.coursecanvasspring.controller;

import com.example.coursecanvasspring.entity.code.*;
import com.example.coursecanvasspring.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

import static com.example.coursecanvasspring.constants.StringConstants.*;

@RestController
@RequestMapping
public class CodeController {

    @Autowired
    private CodeService codeService;

    @PostMapping(ADD_COMPANY_ROUTE)
    public ResponseEntity<?> addCompany(@RequestBody Map<String,String> req){
        Company company = codeService.addCompany(req);
        return ResponseEntity.ok(company);
    }

    @PostMapping(ADD_TOPIC_ROUTE)
    public ResponseEntity<?> addTopic(@RequestBody Map<String,String> req){
        Topic topic = codeService.addTopic(req);
        return ResponseEntity.ok(topic);
    }

    @PostMapping(ADD_PROBLEM_ROUTE)
    public ResponseEntity<?> addProblem(@RequestBody Map<String,String> req){
        Problem problem = codeService.addProblem(req);
        return ResponseEntity.ok(problem);
    }

    @PostMapping(CREATE_SUBMISSION_ROUTE)
    public ResponseEntity<?> createSubmission(@PathVariable String problemId, @RequestBody Map<String,String> req) throws IOException {
        Submission submission = codeService.createSubmission(problemId, req);
        return ResponseEntity.ok(submission);
    }

    @GetMapping(GET_SUBMISSION_RESULT_ROUTE)
    public ResponseEntity<?> getSubmissionResult(@PathVariable String submissionId){
        Submission submission = codeService.getSubmissionResult(submissionId);
        return ResponseEntity.ok(submission);
    }
}
