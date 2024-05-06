package com.hris.HRIS.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.dto.QuizAnswer;
import com.hris.HRIS.model.EmployeeQuizModel;
import com.hris.HRIS.model.QuizModel;
import com.hris.HRIS.model.QuizQuestionModel;
import com.hris.HRIS.repository.EmployeeQuizRepository;
import com.hris.HRIS.repository.QuizRepository;

import com.hris.HRIS.service.LmsModuleMarksEvaluationService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@RequestMapping("/api/v1/lms/course/module/quiz")
public class EmployeeQuizController {
    @Autowired
    EmployeeQuizRepository employeeQuizRepository;

    @Autowired
    QuizController quizController;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizQuestionController quizQuestionController;

    @Autowired
    CourseController courseController;

    @Autowired
    CourseModuleController courseModuleController;
    @Autowired
    private LmsModuleMarksEvaluationService lmsModuleMarksEvaluationService;

    @PostMapping("/attempt")
    public ArrayList<Object> attemptQuiz(@RequestBody String requestBody){
        ObjectMapper objectMapper = new ObjectMapper();

        List<QuizQuestionModel> questionsList = new ArrayList<>();
        ArrayList<Object> result = new ArrayList<>();

        String returnMsg = "";

        try {
            JsonNode requestBodyJson = objectMapper.readTree(requestBody);
            String quizId = requestBodyJson.get("quizId").asText();
            String employeeEmail = requestBodyJson.get("employeeEmail").asText();
            
            Optional<QuizModel> quizModalOptional = quizRepository.findById(quizId);

            if(quizModalOptional.isPresent()){
                String courseId = courseModuleController.getCourseModuleById(quizModalOptional.get().getModuleId()).getBody().getCourseId();

                if(courseController.checkIsCourseUsersExists(courseId, employeeEmail)){
                    questionsList = quizQuestionController.getAllQuizQuestionsByQuizId(quizId);

                    // Remove the key "isCorrect" from the response to make sure that the correct answers are not sent to the frontend during a attempt.
                    for (QuizQuestionModel questionObj : questionsList){
                        ArrayList<HashMap<String, Object>> options = (ArrayList<HashMap<String, Object>>) questionObj.getOptions();

                        for (HashMap<String, Object> option : options) {
                            option.remove("isCorrect");
                        }
                    }

                    if(quizModalOptional.get().getIsRandomized()){
                        Collections.shuffle(questionsList);
                    }

                    Integer totalAttemptsTaken = 0;
                    Boolean isAttemptInProgress = false;
                    String existingInprogressAttemptId = "";

                    for(EmployeeQuizModel existingAttempt : getAllQuizAttemptsById(requestBody)){
                        if(existingAttempt.getStatus().equals("Inprogress")){
                            isAttemptInProgress = true;
                            existingInprogressAttemptId = existingAttempt.getId();
                        }
                        totalAttemptsTaken++;
                    }

                    // Create a new attempt.
                    if(!isAttemptInProgress){
                        if(totalAttemptsTaken < quizModalOptional.get().getNoOfAttempts()){
                            EmployeeQuizModel employeeQuizModel = new EmployeeQuizModel();

                            employeeQuizModel.setEmployeeEmail(employeeEmail);
                            employeeQuizModel.setQuizId(quizId);
                            // employeeQuizModel.setAttempt(1);
                            employeeQuizModel.setAttemptDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            employeeQuizModel.setSubmittedDateTime("0000-00-00 00:00:00");
                            employeeQuizModel.setStatus("Inprogress");

                            returnMsg = employeeQuizRepository.save(employeeQuizModel).getId();
                        }else{
                            returnMsg = "You have reached the maximum number of attempts allowed for this quiz.";
                            questionsList.clear();
                        }
                    }else{
                        returnMsg = existingInprogressAttemptId;
                    }
                }else{
                    returnMsg = "The requested user does not exists in the course";
                }
            }else{
                returnMsg = "The requested quiz does not exists.";
            }
        } catch (Exception e) {
            returnMsg = "Failed to handle the received parameters";
        }

        result.add(returnMsg);
        result.add(questionsList);

        return result;
    }

    @GetMapping("/attempts/get/all")
    public List<EmployeeQuizModel> getAllQuizAttemptsById(@RequestBody String requestBody){
        ObjectMapper objectMapper = new ObjectMapper();

        List<EmployeeQuizModel> attempsList = new ArrayList<>();

        try {
            JsonNode requestBodyJson = objectMapper.readTree(requestBody);
            String quizId = requestBodyJson.get("quizId").asText();
            String employeeEmail = requestBodyJson.get("employeeEmail").asText();


            for(EmployeeQuizModel attempt : employeeQuizRepository.findAllByEmployeeEmail(employeeEmail)){
                if (attempt.getQuizId().equals(quizId)){
                    attempsList.add(attempt);
                }
            }

        } catch (Exception e) {
            // No action to take.
            System.out.println(e);
        }

        return attempsList;
    }

    @PostMapping("/submit")
    public ResponseEntity<ApiResponse> submitQuiz(@RequestBody String requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();

        String returnMsg = "";

        try {
            JsonNode requestBodyJson = objectMapper.readTree(requestBody);
            String quizId = requestBodyJson.get("quizId").asText();
            String employeeEmail = requestBodyJson.get("employeeEmail").asText();
            List<QuizAnswer> answers = objectMapper.convertValue(requestBodyJson.get("answers"), new TypeReference<List<QuizAnswer>>() {});

            Boolean isQuizSubmitted = false;

            // TODO: avoid submissions after the due date/time.

            for(EmployeeQuizModel existingAttempt : getAllQuizAttemptsById(requestBody)){
                if(existingAttempt.getStatus().equals("Inprogress")){
                    existingAttempt.setAnswers(answers);
                    existingAttempt.setSubmittedDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    existingAttempt.setStatus("Submitted");

                    employeeQuizRepository.save(existingAttempt);
                    isQuizSubmitted = true;

                    lmsModuleMarksEvaluationService.evaluateQuizAnswers(existingAttempt);

                    break;
                }
            }

            returnMsg = isQuizSubmitted ? "Quiz submitted successfully." : "Inprogress attempt not found.";
            
        } catch (Exception e) {
            returnMsg = "Failed to submit the quiz.";
        }
        
        ApiResponse apiResponse = new ApiResponse(returnMsg);
        return ResponseEntity.ok(apiResponse);
    }
}