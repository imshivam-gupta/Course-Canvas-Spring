package com.example.coursecanvasspring.repository.code;

import com.example.coursecanvasspring.entity.code.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProblemRepository extends MongoRepository<Problem,String> {
}
