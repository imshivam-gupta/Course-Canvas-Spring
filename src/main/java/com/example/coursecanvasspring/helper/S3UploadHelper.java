package com.example.coursecanvasspring.helper;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class S3UploadHelper {

    public static String uploadFile(MultipartFile file, AmazonS3 s3Client, String bucketName) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            s3Client.putObject(bucketName, fileName, file.getInputStream(), null);
            return s3Client.getUrl(bucketName, fileName).toString();
        } else{
            throw new RuntimeException("File is empty");
        }
    }
}
