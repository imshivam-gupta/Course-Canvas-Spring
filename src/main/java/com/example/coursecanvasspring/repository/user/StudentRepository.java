package com.example.coursecanvasspring.repository.user;

import com.example.coursecanvasspring.entity.user.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findByEmail(String email);
}
