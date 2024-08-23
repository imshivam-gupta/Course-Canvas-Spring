package com.example.coursecanvasspring.repository.user;

import com.example.coursecanvasspring.entity.user.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TeacherRepository extends MongoRepository<Teacher, String> {
    Optional<Teacher> findByEmail(String email);
}
