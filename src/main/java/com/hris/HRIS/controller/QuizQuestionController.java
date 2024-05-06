package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.QuizModel;
import com.hris.HRIS.model.QuizQuestionModel;
import com.hris.HRIS.repository.QuizQuestionRepository;
import com.hris.HRIS.repository.QuizRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/lms/course/module/quiz/questions")
public class QuizQuestionController {
    @Autowired
    QuizQuestionRepository quizQuestionRepository;

    @Autowired
    QuizRepository quizRepository;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addQuestionToQuiz(@RequestBody QuizQuestionModel quizQuestionModel){
        
        String returnMsg;
        Optional<QuizModel> quizModelOptional = quizRepository.findById(quizQuestionModel.getQuizId());

        if(quizModelOptional.isPresent()){

            quizQuestionRepository.save(quizQuestionModel);
            
            returnMsg = "Question added to the quiz successfully.";
            
            
        }else{
            returnMsg = "Quiz not found.";
        }

        ApiResponse apiResponse = new ApiResponse(returnMsg);
        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<QuizQuestionModel> getQuizQuestionById(@PathVariable String id){
        Optional<QuizQuestionModel> quizQuestionModalOptional = quizQuestionRepository.findById(id);

        return quizQuestionModalOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/all/quizId/{quizId}")
    public List<QuizQuestionModel> getAllQuizQuestionsByQuizId(@PathVariable String quizId){

        List<QuizQuestionModel> quizeQuestionsList = quizQuestionRepository.findAllByQuizId(quizId);

        return quizeQuestionsList;
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateQuizQuestion(@PathVariable String id, @RequestBody QuizQuestionModel quizQuestionModel){
        Optional<QuizQuestionModel> quizQuestionModalOptional = quizQuestionRepository.findById(id);

        if(quizQuestionModalOptional.isPresent()){
            QuizQuestionModel existingQuizQuestion = quizQuestionModalOptional.get();
            existingQuizQuestion.setQuestion(quizQuestionModel.getQuestion());
            existingQuizQuestion.setOptions(quizQuestionModel.getOptions());
            existingQuizQuestion.setIsMultipleAnswersAllowed(quizQuestionModel.getIsMultipleAnswersAllowed());
            existingQuizQuestion.setScoreAllowed(quizQuestionModel.getScoreAllowed());
            existingQuizQuestion.setPreLinkedQuizQuestionId(quizQuestionModel.getPreLinkedQuizQuestionId());

            quizQuestionRepository.save(existingQuizQuestion);
        }

        ApiResponse apiResponse = new ApiResponse("Question updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteQuizQuestion(@PathVariable String id){
        quizQuestionRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Question deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
