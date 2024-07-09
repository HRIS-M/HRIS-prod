package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.QuizModel;
import com.hris.HRIS.repository.QuizRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/lms/course/module/quiz")
public class QuizController {
    @Autowired
    QuizRepository quizRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveQuiz(@RequestBody QuizModel quizModel) {
        quizModel.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        quizRepository.save(quizModel);

        ApiResponse apiResponse = new ApiResponse("Quiz created successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<QuizModel> getQuizById(@PathVariable String id){
        Optional<QuizModel> quizModalOptional = quizRepository.findById(id);

        return quizModalOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/all/moduleId/{moduleId}")
    public List<QuizModel> getAllQuizesByModuleId(@PathVariable String moduleId){

        List<QuizModel> quizesList = quizRepository.findAllByModuleId(moduleId);

        return quizesList;
    }

    // @PostMapping("/{quizId}/questions/add")
    // public ResponseEntity<ApiResponse> addQuestionToQuiz(@PathVariable String quizId, @RequestBody String requestBody){
        
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     String returnMsg = "";
        
    //     Optional<QuizModel> quizModelOptional = quizRepository.findById(quizId);

    //     if(quizModelOptional.isPresent()){

    //         JSONArray questions;
            
    //         // if(((JSONArray) quizModelOptional.get().getQuestions()) != null){
    //         //     for(int i = 0; i < quizModelOptional.get().getQuestions().length(); i++){
    //         //         JSONObject elem = quizModelOptional.get().getQuestions().getJSONObject(i);
    //         //         questions.put(elem);
    //         //     }
    //         // }

    //         if((quizModelOptional.get().getQuestions()) != null){
    //             questions = new JSONArray(quizModelOptional.get().getQuestions());
    //         }else{
    //             questions = new JSONArray();
    //         }

    //         try{
    //             JsonNode requestBodyJson = objectMapper.readTree(requestBody);

    //             JSONObject question = new JSONObject();

    //             if(requestBodyJson.get("questionType").asText().equals("multiple choice")){
    //                 question.put("question", requestBodyJson.get("question").asText());
    //                 question.put("options", requestBodyJson.get("options"));
    //                 question.put("questionType", requestBodyJson.get("questionType").asText());
    //                 question.put("isMultipleAnswersAllowed", requestBodyJson.get("isMultipleAnswersAllowed").asBoolean());
    //             }

    //             questions.put(question);

    //             quizModelOptional.get().setQuestions(questions.toList().stream()
    //                     .map(Object::toString)
    //                     .collect(Collectors.toList()));

    //             quizRepository.save(quizModelOptional.get());

    //             returnMsg = "Question added to the quiz successfully";
                
    //         }catch (Exception e){
    //             returnMsg = "Failed to add the question to the quiz.";
    //             System.out.println(e);
    //         }
            
    //     }else{
    //         returnMsg = "Quiz not found.";
    //     }

    //     ApiResponse apiResponse = new ApiResponse(returnMsg);
    //     return ResponseEntity.ok(apiResponse);

    // }

    // @GetMapping("/{quizId}/questions/view")
    // public ResponseEntity<Object> getAllCourseUsers(@PathVariable String quizId){
    //     Optional<QuizModel> quizModelOptional = quizRepository.findById(quizId);
        

    //     ObjectMapper objectMapper = new ObjectMapper();
    //     String returnMsg = "";

    //     JSONArray usersList;

    //     if (quizModelOptional.isPresent()){
    //         usersList = new JSONArray(quizModelOptional.get().getQuestions());

    //         // for(int i = 0; i < users.size(); i++){
    //         //     JSONObject user = new JSONObject();

    //         //     user.put("email", users.get(i).getVariable("email"));
    //         //     user.put("role", users.get(i).getVariable("role"));

    //         //     usersList.put(user);
    //         // }
    //     }else{
    //         usersList = new JSONArray();
    //     }

    //     JSONObject jsonObject = usersList.getJSONObject(0);
    //     // System.out.println(jsonObject);
    //     return ResponseEntity.ok(usersList.toList());
    // }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateQuiz(@PathVariable String id, @RequestBody QuizModel quizModel){
        Optional<QuizModel> quizModalOptional = quizRepository.findById(id);

        if(quizModalOptional.isPresent()){
            QuizModel existingQuiz = quizModalOptional.get();
            existingQuiz.setModuleId(quizModel.getModuleId());
            existingQuiz.setQuizName(quizModel.getQuizName());
            existingQuiz.setQuizDescription(quizModel.getQuizDescription());
            existingQuiz.setNoOfAttempts(quizModel.getNoOfAttempts());
            existingQuiz.setMaximumGradeAllowed(quizModel.getMaximumGradeAllowed());
            existingQuiz.setIsRandomized(quizModel.getIsRandomized());
            existingQuiz.setStartDate(quizModel.getStartDate());
            existingQuiz.setEndDate(quizModel.getEndDate());
            existingQuiz.setStatus(quizModel.getStatus());

            quizRepository.save(existingQuiz);
        }

        ApiResponse apiResponse = new ApiResponse("Quiz updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteQuiz(@PathVariable String id){
        quizRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Quiz deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
