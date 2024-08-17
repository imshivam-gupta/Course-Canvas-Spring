package com.example.coursecanvasspring.helper;

public class StringConstants {
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String INSTRUCTOR_ROLE = "INSTRUCTOR";
    public static final String STUDENT_ROLE = "STUDENT";
    public static final String ASSISTANT_ROLE = "ASSISTANT";

    public static final String LOCAL_STRATEGY = "LOCAL";
    public static final String GOOGLE_STRATEGY = "GOOGLE";

    public static final String OAUTHUSER_EMAIL_ATTRIBUTE = "email";
    public static final String OAUTHUSER_PROFILEPIC_ATTRIBUTE = "picture";
    public static final String OAUTHUSER_NAME_ATTRIBUTE = "name";
    public static final String OUATHUSER_DUMMY_PASSWORD = "dummy";

    // Permitted Routes
    public static final String[] PERMITTED_ROUTES = {
            "/generateToken", "/signup"
    };

    // Config Constants
    public static final String AWS_S3_ACCESS_KEY_ID = "${aws.s3.accessKeyId}";
    public static final String AWS_S3_SECRET_KEY = "${aws.s3.secretKey}";
    public static final String AWS_S3_REGION = "${aws.s3.region}";


    // Bucket Constants
    public static final String BUCKET_NAME = "course-canvas";
}
