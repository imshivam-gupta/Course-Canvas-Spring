package com.example.coursecanvasspring.constants;

public class StringConstants {

    // CHAPTER TYPES
    public static final String CHAPTER_TYPE_VIDEO = "VIDEO";
    public static final String CHAPTER_TYPE_DOCUMENT = "DOCUMENT";
    public static final String CHAPTER_TYPE_QUIZ = "QUIZ";
    public static final String CHAPTER_TYPE_ASSIGNMENT = "ASSIGNMENT";
    public static final String CHAPTER_TYPE_CODE = "CODE";

    // MONGO COLLECTIONS
    public static final String CATEGORY_COLLECTION = "Category";
    public static final String CHAPTER_COLLECTION = "Chapter";
    public static final String COURSE_COLLECTION = "Course";
    public static final String COMPANY_COLLECTION = "Company";
    public static final String PROBLEM_COLLECTION = "Problem";
    public static final String SECTION_COLLECTION = "Section";
    public static final String SUBMISSION_COLLECTION = "Submission";
    public static final String TOPIC_COLLECTION = "Topic";
    public static final String USER_COLLECTION = "User";
    public static final String TRANSACTION_COLLECTION = "Transaction";

    // Authentication Strategies
    public static final String LOCAL_STRATEGY = "LOCAL";
    public static final String GOOGLE_STRATEGY = "GOOGLE";

    // Permitted Routes
    public static final String[] PERMITTED_ROUTES = {"/auth/login", "/auth/register", "/auth/google", "/auth/google/callback", "/auth/verify-email", "/auth/forgot-password", "/auth/reset-password", "/auth/refresh-token", "/auth/logout"};

    // USER ROLES
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String INSTRUCTOR_ROLE = "INSTRUCTOR";
    public static final String STUDENT_ROLE = "STUDENT";
    public static final String ASSISTANT_ROLE = "ASSISTANT";

    // SUBMISSION STATUS
    public static final String SUBMISSION_STATUS_PENDING = "PENDING";
    public static final String SUBMISSION_STATUS_ACCEPTED = "ACCEPTED";
    public static final String SUBMISSION_STATUS_REJECTED = "REJECTED";


    // Chapter Fields
    public static final String TITLE_CHAPTER_FIELD = "title";
    public static final String DESCRIPTION_CHAPTER_FIELD = "description";
    public static final String POSITION_CHAPTER_FIELD = "position";
    public static final String PUBLISHED_CHAPTER_FIELD = "isPublished";
    public static final String FREE_CHAPTER_FIELD = "isFree";
    public static final String CONTENT_TYPE_CHAPTER_FIELD = "contentType";
    public static final String ARTICLE_URL_CHAPTER_FIELD = "articleUrl";
    public static final String VIDEO_URL_CHAPTER_FIELD = "videoUrl";
    public static final String THUMBNAIL_URL_CHAPTER_FIELD = "thumbnailUrl";
    public static final String DURATION_CHAPTER_FIELD = "duration";
    public static final String VIDEO_TYPE_CHAPTER_FIELD = "videoType";
    public static final String VIDEO_QUALITY_CHAPTER_FIELD = "videoQuality";
    public static final String VIDEO_SIZE_CHAPTER_FIELD = "videoSize";

    // Necessary Fields
    public static final String[] CHAPTER_CREATE_NOT_NULL_FIELDS = {TITLE_CHAPTER_FIELD, CONTENT_TYPE_CHAPTER_FIELD};
    public static final String[] DOC_CHAPTER_CREATE_NOT_NULL_FIELDS = {ARTICLE_URL_CHAPTER_FIELD};
    public static final String[] VIDEO_CHAPTER_CREATE_NOT_NULL_FIELDS = {VIDEO_URL_CHAPTER_FIELD,DURATION_CHAPTER_FIELD,THUMBNAIL_URL_CHAPTER_FIELD};


    // Section Fields
    public static final String TITLE_SECTION_FIELD = "title";
    public static final String DESCRIPTION_SECTION_FIELD = "description";
    public static final String BANNER_URL_SECTION_FIELD = "bannerUrl";
    public static final String POSITION_SECTION_FIELD = "position";
    public static final String PUBLISHED_SECTION_FIELD = "isPublished";
    public static final String FREE_SECTION_FIELD = "isFree";
    public static final String CHAPTERS_SECTION_FIELD = "chapters";

   // Necessary Fields
    public static final String[] SECTION_CREATE_NOT_NULL_FIELDS = {TITLE_SECTION_FIELD};

    // Course Category Fields
    public static final String NAME_CATEGORY_FIELD = "name";
    public static final String DESCRIPTION_CATEGORY_FIELD = "description";

    // Necessary Fields
    public static final String[] CATEGORY_CREATE_NOT_NULL_FIELDS = {NAME_CATEGORY_FIELD};

    // Course Fields
    public static final String TITLE_COURSE_FIELD = "title";
    public static final String DESCRIPTION_COURSE_FIELD = "description";
    public static final String BANNER_URL_COURSE_FIELD = "bannerUrl";
    public static final String PUBLISHED_COURSE_FIELD = "isPublished";
    public static final String FREE_COURSE_FIELD = "isFree";
    public static final String PRICE_COURSE_FIELD = "price";
    public static final String DISCOUNT_COURSE_FIELD = "discount";

    // Necessary Fields
    public static final String[] COURSE_CREATE_NOT_NULL_FIELDS = {TITLE_COURSE_FIELD,DESCRIPTION_COURSE_FIELD,PRICE_COURSE_FIELD,PUBLISHED_COURSE_FIELD,FREE_COURSE_FIELD};

    // Company Fields
    public static final String NAME_COMPANY_FIELD = "name";

    // Necessary Fields
    public static final String[] COMPANY_CREATE_NOT_NULL_FIELDS = {NAME_COMPANY_FIELD};

    // Topic Fields
    public static final String TITLE_TOPIC_FIELD = "title";
    public static final String DESCRIPTION_TOPIC_FIELD = "description";

    // Necessary Fields
    public static final String[] TOPIC_CREATE_NOT_NULL_FIELDS = {TITLE_TOPIC_FIELD};

    // Problem Fields
    public static final String TITLE_PROBLEM_FIELD = "title";
    public static final String DESCRIPTION_PROBLEM_FIELD = "descriptionUrl";
    public static final String DIFFICULTY_PROBLEM_FIELD = "difficulty";
    public static final String HINTS_PROBLEM_FIELD = "hints";
    public static final String EDITORIAL_PROBLEM_FIELD = "editorialUrl";
    public static final String SUPPORTED_LANGUAGES_PROBLEM_FIELD = "supportedLanguages";
    public static final String RELATED_PROBLEMS_PROBLEM_FIELD = "relatedProblems";
    public static final String TOPICS_PROBLEM_FIELD = "topics";
    public static final String COMPANIES_PROBLEM_FIELD = "companies";

    // Necessary Fields
    public static final String[] PROBLEM_CREATE_NOT_NULL_FIELDS = {TITLE_PROBLEM_FIELD,DESCRIPTION_PROBLEM_FIELD,DIFFICULTY_PROBLEM_FIELD};

    // Submission Fields
    public static final String CODE_SUBMISSION_FIELD = "code";
    public static final String LANGUAGE_SUBMISSION_FIELD = "language";
    public static final String STATUS_SUBMISSION_FIELD = "status";
    public static final String TOKENS_SUBMISSION_FIELD = "tokens";
    public static final String TEST_CASES_SUBMISSION_FIELD = "testCases";

    // Necessary Fields
    public static final String[] SUBMISSION_CREATE_NOT_NULL_FIELDS = {CODE_SUBMISSION_FIELD,LANGUAGE_SUBMISSION_FIELD};

    // Request Body Keys
    public static final String STDIN_KEY = "stdin";
    public static final String EXPECTED_OUTPUT_KEY = "expectedOutput";

    // Response Body Keys
    public static final String SUBMISSION_TOKEN_KEY = "token";

    // Error Messages
    public static final String INVALID_REQUEST_MISSING_REQUIRED_FIELDS = "Invalid request missing required fields";

    // S3 Paths
    public static final String PROBLEM_OUTPUT_PATH = "problems/%s/tests/outputs/";
    public static final String PROBLEM_INPUT_PATH = "problems/%s/tests/inputs/";
    public static final String BOILERPLATE_PATH = "problems/%s/boilerplate-full/function.%s";
}
