package com.example.coursecanvasspring.repository.section;

import com.example.coursecanvasspring.entity.section.Section;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SectionRepository extends MongoRepository<Section, String> {

    @Query("{ 'chapters._id' : ?0 }")
    Section findByChapterId(String chapterId);
}
