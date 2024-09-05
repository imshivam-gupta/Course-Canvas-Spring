package com.example.coursecanvasspring.repository.user;

import com.example.coursecanvasspring.entity.user.User;
import com.example.coursecanvasspring.enums.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    List<User> findAllByStatus(Status status);
}
