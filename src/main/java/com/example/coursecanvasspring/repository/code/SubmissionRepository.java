package com.example.coursecanvasspring.repository.code;

import com.example.coursecanvasspring.entity.code.Submission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubmissionRepository extends MongoRepository<Submission, String> {
}
