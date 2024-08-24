package com.example.coursecanvasspring.helper;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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

    public static List<String> listAndReadS3Objects(String prefix,String bucketName,AmazonS3 s3Client) throws IOException {
        List<String> contentList = new ArrayList<>();

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucketName)
                .withPrefix(prefix);

        com.amazonaws.services.s3.model.ObjectListing objectListing;
        do {
            objectListing = s3Client.listObjects(listObjectsRequest);
            objectListing.getObjectSummaries().forEach(s3ObjectSummary -> {
                try {
                    String content = readS3ObjectAsString(s3ObjectSummary.getKey(),s3Client,bucketName);
                    contentList.add(content);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            listObjectsRequest.setMarker(objectListing.getNextMarker());
        } while (objectListing.isTruncated());

        return contentList;
    }

    public static String readS3ObjectAsString(String key, AmazonS3 s3Client, String bucketName) throws IOException {
        S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, key));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();
        }
    }
}
