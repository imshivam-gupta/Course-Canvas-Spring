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
    public static final String ORDER_COLLECTION = "Order";
    public static final String COUPON_COLLECTION = "Coupon";
    public static final String ENROLLED_COURSE_COLLECTION = "EnrolledCourse";


    // Authentication Strategies
    public static final String LOCAL_STRATEGY = "LOCAL";
    public static final String GOOGLE_STRATEGY = "GOOGLE";

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
    public static final String PROBLEM_ID_CHAPTER_FIELD = "problemId";

    // Necessary Fields
    public static final String[] CHAPTER_CREATE_NOT_NULL_FIELDS = {TITLE_CHAPTER_FIELD, CONTENT_TYPE_CHAPTER_FIELD};
    public static final String[] DOC_CHAPTER_CREATE_NOT_NULL_FIELDS = {ARTICLE_URL_CHAPTER_FIELD};
    public static final String[] VIDEO_CHAPTER_CREATE_NOT_NULL_FIELDS = {VIDEO_URL_CHAPTER_FIELD};
    public static final String[] CODE_CHAPTER_CREATE_NOT_NULL_FIELDS = {PROBLEM_ID_CHAPTER_FIELD};

    public static final String[] DOC_CHAPTER_PUBLISH_NOT_NULL_FIELDS = {ARTICLE_URL_CHAPTER_FIELD};
    public static final String[] VIDEO_CHAPTER_PUBLISH_NOT_NULL_FIELDS = {VIDEO_URL_CHAPTER_FIELD,DURATION_CHAPTER_FIELD,THUMBNAIL_URL_CHAPTER_FIELD};

    // Section Fields
    public static final String TITLE_SECTION_FIELD = "title";
    public static final String DESCRIPTION_SECTION_FIELD = "description";
    public static final String BANNER_URL_SECTION_FIELD = "bannerUrl";
    public static final String POSITION_SECTION_FIELD = "position";
    public static final String PUBLISHED_SECTION_FIELD = "isPublished";
    public static final String FREE_SECTION_FIELD = "isFree";
    public static final String CHAPTERS_SECTION_FIELD = "chapters";
    public static final String DAYS_TO_COMPLETE_SECTION_FIELD = "numOfDaysToComplete";

    // Necessary Fields
    public static final String[] SECTION_CREATE_NOT_NULL_FIELDS = {TITLE_SECTION_FIELD};
    public static final String[] SECTION_PUBLISH_NOT_NULL_FIELDS = {TITLE_SECTION_FIELD,DAYS_TO_COMPLETE_SECTION_FIELD,BANNER_URL_SECTION_FIELD,POSITION_SECTION_FIELD};

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
    public static final String CATEGORY_COURSE_FIELD = "category";

    // Necessary Fields
    public static final String[] COURSE_CREATE_NOT_NULL_FIELDS = {TITLE_COURSE_FIELD,DESCRIPTION_COURSE_FIELD,PRICE_COURSE_FIELD,PUBLISHED_COURSE_FIELD,FREE_COURSE_FIELD,CATEGORY_COURSE_FIELD};
    public static final String[] COURSE_PUBLISH_NOT_NULL_FIELDS = {TITLE_COURSE_FIELD,DESCRIPTION_COURSE_FIELD,BANNER_URL_COURSE_FIELD,CATEGORY_COURSE_FIELD};

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
    public static final String BOILERPLATE_PATH_FULL = "problems/%s/boilerplate-full/function.%s";
    public static final String BOILERPLATE_PATH = "problems/%s/boilerplate/function.%s";

    // AWS S3 Constants
    public static final String AWS_S3_ACCESS_KEY_ID = "${aws.s3.accessKeyId}";
    public static final String AWS_S3_SECRET_KEY = "${aws.s3.secretKey}";
    public static final String AWS_S3_REGION = "${aws.s3.region}";

    // API Routes
    public static final String API_PREFIX = "/api";

    // Auth Routes
    public static final String SIGNUP_ROUTE = API_PREFIX + "/signup";
    public static final String LOGIN_ROUTE = API_PREFIX + "/login";

    // User Routes
    public static final String USER_ROUTE = API_PREFIX + "/user";
    public static final String USER_PROFILE_PICTURE_ROUTE = "/profilePicture";
    public static final String STUDENT_ENROLLED_COURSES_ROUTE =  "/enrolledCourses";

    // Chapter Routes
    public static final String CHAPTER_ROUTE_PREFIX = API_PREFIX + "/chapter";
    public static final String GET_CHAPTER_ROUTE = CHAPTER_ROUTE_PREFIX + "/{chapterId}";
    public static final String CREATE_CHAPTER_ROUTE = CHAPTER_ROUTE_PREFIX + "/{sectionId}/create";
    public static final String UPDATE_CHAPTER_ROUTE = CHAPTER_ROUTE_PREFIX + "/{chapterId}/update";
    public static final String PUBLISH_CHAPTER_ROUTE = CHAPTER_ROUTE_PREFIX + "/{chapterId}/publish";
    public static final String DELETE_CHAPTER_ROUTE = CHAPTER_ROUTE_PREFIX + "/{sectionId}/{chapterId}/delete";
    public static final String UNPUBLISH_CHAPTER_ROUTE = CHAPTER_ROUTE_PREFIX + "/{sectionId}/{chapterId}/unpublish";
    public static final String REORDER_CHAPTER_ROUTE = CHAPTER_ROUTE_PREFIX + "/{sectionId}/reorder";

    // Section Routes
    public static final String SECTION_ROUTE_PREFIX = API_PREFIX + "/section";
    public static final String GET_SECTION_ROUTE = SECTION_ROUTE_PREFIX + "/{sectionId}";
    public static final String CREATE_SECTION_ROUTE = SECTION_ROUTE_PREFIX + "/{courseId}/create";
    public static final String BANNER_SECTION_ROUTE = SECTION_ROUTE_PREFIX + "/{sectionId}/banner";
    public static final String UPDATE_SECTION_ROUTE = SECTION_ROUTE_PREFIX + "/{sectionId}/update";
    public static final String PUBLISH_SECTION_ROUTE = SECTION_ROUTE_PREFIX + "/{sectionId}/publish";
    public static final String DELETE_SECTION_ROUTE = SECTION_ROUTE_PREFIX + "/{courseId}/{sectionId}/delete";
    public static final String UNPUBLISH_SECTION_ROUTE = SECTION_ROUTE_PREFIX + "/{courseId}/{sectionId}/unpublish";
    public static final String REORDER_SECTION_ROUTE = SECTION_ROUTE_PREFIX + "/{courseId}/reorder";

    // Course Routes
    public static final String COURSE_ROUTE_PREFIX = API_PREFIX + "/course";
    public static final String GET_COURSE_ROUTE = COURSE_ROUTE_PREFIX + "/{courseId}";
    public static final String CREATE_CATEGORY_ROUTE = COURSE_ROUTE_PREFIX + "/createCategory";
    public static final String CREATE_COURSE_ROUTE = COURSE_ROUTE_PREFIX + "/create";
    public static final String BANNER_COURSE_ROUTE = COURSE_ROUTE_PREFIX + "/{courseId}/banner";
    public static final String UPDATE_COURSE_ROUTE = COURSE_ROUTE_PREFIX + "/{courseId}/update";
    public static final String PUBLISH_COURSE_ROUTE = COURSE_ROUTE_PREFIX + "/{courseId}/publish";
    public static final String GET_COURSES_ROUTE = COURSE_ROUTE_PREFIX;
    public static final String GET_COURSE_CATEGORIES_ROUTE = COURSE_ROUTE_PREFIX + "/categories";

    // Code Routes
    public static final String CODE_ROUTE_PREFIX = API_PREFIX + "/code";
    public static final String ADD_COMPANY_ROUTE = CODE_ROUTE_PREFIX + "/addCompany";
    public static final String ADD_TOPIC_ROUTE = CODE_ROUTE_PREFIX + "/addTopic";
    public static final String ADD_PROBLEM_ROUTE = CODE_ROUTE_PREFIX + "/addProblem";
    public static final String CREATE_SUBMISSION_ROUTE = CODE_ROUTE_PREFIX + "/{problemId}/createSubmission";
    public static final String GET_SUBMISSION_RESULT_ROUTE = CODE_ROUTE_PREFIX + "/{submissionId}/getSubmissionResult";

    // Order Routes
    public static final String ORDER_ROUTE_PREFIX = API_PREFIX + "/order";
    public static final String CREATE_ORDER_ROUTE = ORDER_ROUTE_PREFIX + "/create";
    public static final String PAY_ORDER_ROUTE = ORDER_ROUTE_PREFIX + "/{orderId}/pay";

    // Coupon Routes
    public static final String COUPON_ROUTE_PREFIX = API_PREFIX + "/coupon";
    public static final String CREATE_COUPON_ROUTE = COUPON_ROUTE_PREFIX + "/create";

    // Notion Routes
    public static final String NOTION_ROUTE_PREFIX = API_PREFIX + "/notion";
    public static final String GET_NOTION_PAGE_ROUTE = NOTION_ROUTE_PREFIX + "/getNotionPage";

    public static final String WEBSOCKET_ROUTE = "/ws/**";

    // Permitted Routes
    public static final String[] PUBLIC_ROUTES = {
            SIGNUP_ROUTE, LOGIN_ROUTE, GET_COURSES_ROUTE, GET_COURSE_CATEGORIES_ROUTE, GET_COURSE_ROUTE,GET_SECTION_ROUTE, GET_CHAPTER_ROUTE, GET_NOTION_PAGE_ROUTE, WEBSOCKET_ROUTE
    };

    public static final String[] ADMIN_ROUTES = {
            ADD_COMPANY_ROUTE, ADD_TOPIC_ROUTE, ADD_PROBLEM_ROUTE
    };

    public static final String[] INSTRUCTOR_ROUTES = {
            CREATE_CATEGORY_ROUTE,CREATE_COURSE_ROUTE,BANNER_COURSE_ROUTE,CREATE_SECTION_ROUTE,BANNER_SECTION_ROUTE,CREATE_CHAPTER_ROUTE
    };


    // Google OAuth Constants
    public static final String GOOGLE_OAUTH_EMAIL = "email";
    public static final String GOOGLE_OAUTH_PROFILEPIC = "picture";
    public static final String GOOGLE_OAUTH_NAME = "name";
    public static final String OAUTH_DUMMY_PASSWORD = "dummy";

}
