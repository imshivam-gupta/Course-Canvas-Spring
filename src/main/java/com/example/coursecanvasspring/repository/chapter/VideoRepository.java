package com.example.coursecanvasspring.repository.chapter;

import com.example.coursecanvasspring.entity.chapter.VideoChapter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<VideoChapter, String> {
}