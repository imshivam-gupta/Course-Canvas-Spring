package com.example.coursecanvasspring.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.coursecanvasspring.dto.SubmissionResponse;
import com.example.coursecanvasspring.entity.code.*;
import com.example.coursecanvasspring.enums.ProblemDifficulty;
import com.example.coursecanvasspring.repository.code.CompanyRepository;
import com.example.coursecanvasspring.repository.code.ProblemRepository;
import com.example.coursecanvasspring.repository.code.SubmissionRepository;
import com.example.coursecanvasspring.repository.code.TopicRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.*;

import static com.example.coursecanvasspring.constants.StringConstants.*;
import static com.example.coursecanvasspring.helper.RequestValidators.validateRequestKeys;
import static com.example.coursecanvasspring.helper.S3UploadHelper.*;

@Service
public class CodeService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private ObjectMapper objectMapper;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${judge.api.url}")
    private String judgeServiceApiUrl;

    public Company addCompany(Map<String,String> req) {
        if(!validateRequestKeys(COMPANY_CREATE_NOT_NULL_FIELDS, req)){
            throw new RuntimeException(INVALID_REQUEST_MISSING_REQUIRED_FIELDS);
        }

        Company company = new Company();
        company.setName(req.get(NAME_COMPANY_FIELD));
        company = companyRepository.save(company);
        return company;
    }

    public Topic addTopic(Map<String,String> req) {
        if(!validateRequestKeys(TOPIC_CREATE_NOT_NULL_FIELDS, req)){
            throw new RuntimeException(INVALID_REQUEST_MISSING_REQUIRED_FIELDS);
        }

        Topic topic = new Topic();
        topic.setTitle(req.get(TITLE_TOPIC_FIELD));
        topic.setDescription(req.get(DESCRIPTION_TOPIC_FIELD));
        topic = topicRepository.save(topic);
        return topic;
    }

    public Problem addProblem(Map<String,String> req) {
        if(!validateRequestKeys(PROBLEM_CREATE_NOT_NULL_FIELDS, req)){
            throw new RuntimeException(INVALID_REQUEST_MISSING_REQUIRED_FIELDS);
        }

        Problem problem = new Problem();
        problem.setTitle(req.get(TITLE_PROBLEM_FIELD));
        problem.setDescriptionUrl(req.get(DESCRIPTION_PROBLEM_FIELD));
        problem.setDifficulty(ProblemDifficulty.valueOf(req.get(DIFFICULTY_PROBLEM_FIELD)));
        problem = problemRepository.save(problem);
        return problem;
    }

    public Submission createSubmission(String problemId, Map<String, String> req) throws IOException {
        Problem problem = problemRepository.findById(problemId).
                orElseThrow(() -> new RuntimeException("Problem not found"));

        if (!validateRequestKeys(SUBMISSION_CREATE_NOT_NULL_FIELDS, req)) {
            throw new RuntimeException(INVALID_REQUEST_MISSING_REQUIRED_FIELDS);
        }

        Submission submission = new Submission();
        submission.setCode(req.get(CODE_SUBMISSION_FIELD));
        submission.setLanguage(req.get(LANGUAGE_SUBMISSION_FIELD));

        ProblemInternal problemInternal = getProblem(problem.getTitle(), req.get(LANGUAGE_SUBMISSION_FIELD));
        submitToJudgeService(problemInternal, submission);
        submission = submissionRepository.save(submission);

        problem.getSubmissions().add(submission);
        problemRepository.save(problem);

        return submission;
    }

    public Submission getSubmissionResult(String submissionId){
        Submission submission = submissionRepository.findById(submissionId).
                orElseThrow(() -> new RuntimeException("Submission not found"));

        if(submission.getToken() == null){
            throw new RuntimeException("Submission not yet submitted");
        }

        String url = judgeServiceApiUrl + "/submit/" + submission.getToken();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Judge API call failed");
        }

        // Deserialize the JSON response body into a list of TestCaseResponse objects
        List<SubmissionResponse.TestCaseResponse> res;
        try {
            res = objectMapper.readValue(response.getBody(), new TypeReference<List<SubmissionResponse.TestCaseResponse>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error parsing response from Judge API", e);
        }

        // Process the results
        SubmissionResponse submissionResponse = new SubmissionResponse();
        submissionResponse.setTestCases(res);

        assert res != null;
        for(SubmissionResponse.TestCaseResponse result : submissionResponse.getTestCases()){
            if(result.getStatusCode()!=0){
                submission.setStatus("Failed");
                submission.setFailedTestCaseDetails("Test case " + result.getInput() + " failed");
                submissionRepository.save(submission);
                return submission;
            }
        }

        submission.setStatus("Passed");
        submissionRepository.save(submission);
        return submission;
    }



    private void submitToJudgeService(ProblemInternal problem, Submission submissionInput) throws JsonProcessingException {
        String url = judgeServiceApiUrl + "/submit";
        String fullBoilerplateCode = problem.getFullBoilerPlate().replace("##USER_CODE_HERE##", submissionInput.getCode());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> requestPayload = getRequestPayload(problem, submissionInput, fullBoilerplateCode);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestPayload, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String,String> responseBody = new ObjectMapper().readValue(response.getBody(), Map.class);
            submissionInput.setToken(responseBody.get(SUBMISSION_TOKEN_KEY));
        } else {
            throw new RuntimeException("Judge API call failed");
        }
    }

    private static Map<String, Object> getRequestPayload(ProblemInternal problem, Submission submissionInput, String fullBoilerplateCode) {
        Map<String, Object> requestPayload = new HashMap<>();
        List<Map<String, Object>> testcases = new ArrayList<>();

        for (int i = 0; i < problem.getInputs().size(); i++) {
            Map<String, Object> testcase = new HashMap<>();
            testcase.put(STDIN_KEY, problem.getInputs().get(i));
            testcase.put(EXPECTED_OUTPUT_KEY, problem.getOutputs().get(i));
            testcases.add(testcase);
        }

        if (testcases.isEmpty()) {
            throw new RuntimeException("Testcases list cannot be empty");
        }

        requestPayload.put(TEST_CASES_SUBMISSION_FIELD, testcases);
        requestPayload.put(LANGUAGE_SUBMISSION_FIELD, submissionInput.getLanguage());
        requestPayload.put(CODE_SUBMISSION_FIELD, fullBoilerplateCode);
        return requestPayload;
    }

    public ProblemInternal getProblem(String problemName, String languageId) throws IOException {
        String fullBoilerplateCode = getProblemFullBoilerplateCode(problemName, languageId);
        List<String> inputs = getProblemInputs(problemName);
        List<String> outputs = getProblemOutputs(problemName);

        return new ProblemInternal(problemName, fullBoilerplateCode, inputs, outputs);
    }

    public List<BoilerplateCode> getBoilerplateCodes(String problemId) {
        List<String> languageIds = Arrays.asList("cpp", "java", "js","rs","py");
        List<BoilerplateCode> boilerplateCodes = new ArrayList<>();

        for (String languageId : languageIds) {
            try {
                String fullBoilerplateCode = getProblemBoilerplateCode(problemId, languageId);
                boilerplateCodes.add(new BoilerplateCode(fullBoilerplateCode, languageId));
            } catch (IOException e) {
                System.out.println("Error fetching boilerplate code for language: " + languageId);
            }
        }

        return boilerplateCodes;
    }

    private String getProblemFullBoilerplateCode(String problemId, String languageId) throws IOException {
        try{
        String key = String.format(BOILERPLATE_PATH_FULL, problemId, languageId);
        return readS3ObjectAsString(key, amazonS3, bucketName);
        } catch (Exception e) {
            System.out.println("Error fetching boilerplate code for language: " + languageId);
        }
        return "";
    }

    private String getProblemBoilerplateCode(String problemId, String languageId) throws IOException {
        try{
            String key = String.format(BOILERPLATE_PATH, problemId, languageId);
            return readS3ObjectAsString(key, amazonS3, bucketName);
        } catch (Exception e) {
            System.out.println("Error fetching boilerplate code for language: " + languageId);
        }
        return "";
    }

    private List<String> getProblemInputs(String problemName) throws IOException {
        String prefix = String.format(PROBLEM_INPUT_PATH, problemName);
        return listAndReadS3Objects(prefix, bucketName, amazonS3);
    }

    private List<String> getProblemOutputs(String problemName) throws IOException {
        String prefix = String.format(PROBLEM_OUTPUT_PATH, problemName);
        return listAndReadS3Objects(prefix, bucketName, amazonS3);
    }
}
