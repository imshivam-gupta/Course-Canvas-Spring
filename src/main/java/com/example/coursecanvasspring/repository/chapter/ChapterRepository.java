package com.example.coursecanvasspring.repository.chapter;

import com.example.coursecanvasspring.entity.chapter.Chapter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChapterRepository extends MongoRepository<Chapter, String> {
}