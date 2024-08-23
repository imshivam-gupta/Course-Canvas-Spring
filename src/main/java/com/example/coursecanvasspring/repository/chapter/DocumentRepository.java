package com.example.coursecanvasspring.repository.chapter;

import com.example.coursecanvasspring.entity.chapter.DocumentChapter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepository extends MongoRepository<DocumentChapter, String> {
}