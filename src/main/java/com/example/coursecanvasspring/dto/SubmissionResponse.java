package com.example.coursecanvasspring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionResponse {
    private List<TestCaseResponse> testCases;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TestCaseResponse {
        private String _id;
        private String token;
        private String submissionId;
        private Long statusCode;
        private String input;
        private String output;
        private String error;
        private String createdAt;
        private Double memory;
        private Double executionTime;

    }
}
