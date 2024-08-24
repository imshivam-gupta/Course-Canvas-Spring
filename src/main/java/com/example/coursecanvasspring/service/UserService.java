package com.example.coursecanvasspring.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.coursecanvasspring.entity.user.Student;
import com.example.coursecanvasspring.entity.user.Teacher;
import com.example.coursecanvasspring.entity.user.User;
import com.example.coursecanvasspring.repository.user.StudentRepository;
import com.example.coursecanvasspring.repository.user.TeacherRepository;
import com.example.coursecanvasspring.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private AuthorizationService authorizationService;

    @Value("${aws.s3.bucket}")
    private String BUCKET_NAME;

    @Autowired
    private AmazonS3 amazonS3;

    public Optional<User> findUserByEmail() {
        String email = authorizationService.getLoggedInUserEmail();
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<Student> findStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public Optional<Teacher> findTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    private void copyNonNullProperties(User source, User target) {
        if (source.getName() != null) target.setName(source.getName());
        if (source.getPassword() != null) target.setPassword(source.getPassword());
        if (source.getRole() != null) target.setRole(source.getRole());
        if (source.getEmailVerified() != null) target.setEmailVerified(source.getEmailVerified());
        if (source.getProviderId() != null) target.setProviderId(source.getProviderId());
        if (source.getOauthProvider() != null) target.setOauthProvider(source.getOauthProvider());
        if (source.getWebsite() != null) target.setWebsite(source.getWebsite());
        if (source.getYoutube() != null) target.setYoutube(source.getYoutube());
        if (source.getTwitter() != null) target.setTwitter(source.getTwitter());
        if (source.getLinkedin() != null) target.setLinkedin(source.getLinkedin());

        if (source.getReviews() != null && !source.getReviews().isEmpty()) target.setReviews(source.getReviews());
        if (source.getRatings() != null && !source.getRatings().isEmpty()) target.setRatings(source.getRatings());
        if (source.getDiscussions() != null && !source.getDiscussions().isEmpty()) target.setDiscussions(source.getDiscussions());
    }

    public User updateUser(User userUpdate) {
        User existingUser = findUserByEmail()
                .orElseThrow(() -> new RuntimeException("User not found"));

        copyNonNullProperties(userUpdate, existingUser);
        return userRepository.save(existingUser);
    }

    public User editProfilePicture(MultipartFile file) throws IOException {
        User existingUser = findUserByEmail()
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            amazonS3.putObject(BUCKET_NAME, fileName, file.getInputStream(), null);
            String fileUrl = amazonS3.getUrl(BUCKET_NAME, fileName).toString();
            existingUser.setProfilePicture(fileUrl);
        }

        return userRepository.save(existingUser);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
