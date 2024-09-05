package com.example.coursecanvasspring.controller;

import com.example.coursecanvasspring.entity.user.*;
import com.example.coursecanvasspring.enums.UserRole;
import com.example.coursecanvasspring.service.AuthorizationService;
import com.example.coursecanvasspring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import static com.example.coursecanvasspring.constants.StringConstants.*;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(USER_ROUTE)
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping
    public ResponseEntity<?> getUser() {
        User user = userService.findUserByEmail().orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole().equals(UserRole.STUDENT)) {
            Student student = userService.findStudentByEmail(user.getEmail())
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            return ResponseEntity.ok(student);
        } else if (user.getRole().equals(UserRole.INSTRUCTOR)) {
            Teacher teacher = userService.findTeacherByEmail(user.getEmail())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            return ResponseEntity.ok(teacher);
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @PatchMapping
    public ResponseEntity<?> updateUser(@RequestBody User userUpdate) throws IOException {
        userService.findUserByEmail().orElseThrow(() -> new RuntimeException("User not found"));
        User updatedUser =  userService.updateUser(userUpdate);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping(USER_PROFILE_PICTURE_ROUTE)
    public ResponseEntity<?> updateProfilePicture(@RequestParam(value = "file", required = true) MultipartFile file) throws IOException {
        userService.findUserByEmail().orElseThrow(() -> new RuntimeException("User not found"));
        User updatedUser =  userService.editProfilePicture(file);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping(STUDENT_ENROLLED_COURSES_ROUTE)
    public ResponseEntity<?> getStudentEnrolledCourses() {
        User currentUser = authorizationService.getLoggedInUser();
        if(currentUser.getRole() != UserRole.STUDENT) {
            throw new RuntimeException("User is not a student");
        }

        Student student = userService.findStudentByEmail(currentUser.getEmail())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return ResponseEntity.ok(student.getEnrolledCourses());
    }

    @GetMapping(GET_INSTRUCTORS)
    public ResponseEntity<?> getInstructors() {
        return ResponseEntity.ok(userService.getInstructors());
    }

}
