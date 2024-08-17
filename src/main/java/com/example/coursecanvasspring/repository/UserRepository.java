package com.example.coursecanvasspring.repository;

import com.example.coursecanvasspring.entity.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
