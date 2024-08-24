package com.example.coursecanvasspring.repository.code;

import com.example.coursecanvasspring.entity.code.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TopicRepository extends MongoRepository<Topic, String> {
}
