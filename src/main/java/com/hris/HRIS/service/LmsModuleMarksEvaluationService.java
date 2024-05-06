package com.hris.HRIS.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hris.HRIS.dto.QuizAnswer;
import com.hris.HRIS.model.QuizModel;
import com.hris.HRIS.model.QuizQuestionModel;
import com.hris.HRIS.repository.EmployeeQuizRepository;
import com.hris.HRIS.repository.QuizQuestionRepository;
import com.hris.HRIS.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hris.HRIS.model.EmployeeQuizModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LmsModuleMarksEvaluationService {

    @Autowired
    QuizQuestionRepository quizQuestionRepository;

    @Autowired
    EmployeeQuizRepository employeeQuizRepository;

    @Autowired
    QuizRepository quizRepository;

    public void evaluateQuizAnswers(EmployeeQuizModel employeeQuizModel){

        try {
            for (QuizAnswer quizAnswer : employeeQuizModel.getAnswers()) { // Loop through each answered questions
                Optional<QuizQuestionModel> originalQuestionOptional = quizQuestionRepository.findById(quizAnswer.getQuestionId());

                // Set default values.
                quizAnswer.setIsCorrect(false);
                quizAnswer.setScore(0.0);
                int noOfCorrectAnsweredAllowed = 0;
                double scorePerCorrectOption = 0; //For the questions that allowed multiple answers.

                if (originalQuestionOptional.isPresent()) {
                    QuizQuestionModel originalQuestion = originalQuestionOptional.get();

                    ArrayList<Object> options = (ArrayList<Object>) originalQuestion.getOptions();

                    if(originalQuestion.getIsMultipleAnswersAllowed()){

                        for (Object option : options){ // Loop though the options in the original question to identify the number of correct answers allowed for the specific question if it is allows multiple answers.
                            Map<String, Object> optionMap = (Map<String, Object>) option;

                            if((Boolean) optionMap.get("isCorrect")){
                                noOfCorrectAnsweredAllowed++;
                            }
                        }

                        // Equally, divide the marks between each correct option
                        scorePerCorrectOption = originalQuestion.getScoreAllowed()/noOfCorrectAnsweredAllowed;

                    }else{
                        noOfCorrectAnsweredAllowed = 1;
                        scorePerCorrectOption = originalQuestion.getScoreAllowed();
                    }

                    // Loop through the answers provided for the specific question and evaluate them with the options in the original question.
                    for(String answerGiven : quizAnswer.getAnswersGiven()){
                        for (Object option : options){
                            Map<String, Object> optionMap = (Map<String, Object>) option;

                            if(answerGiven.equals(optionMap.get("optionText").toString())){
                                if((Boolean) optionMap.get("isCorrect")){
                                    quizAnswer.setIsCorrect(true);
                                    quizAnswer.setScore(quizAnswer.getScore() + scorePerCorrectOption);
                                }
                                break;
                            }
                        }
                    }
                }

                quizAnswer.setScore(Double.valueOf(String.format("%.2f", quizAnswer.getScore())));
            }

        }catch (Exception e){
            System.out.println(e);
        }

        employeeQuizRepository.save(employeeQuizModel);
        calculateQuizMarks(employeeQuizModel);
    }

    public void calculateQuizMarks(EmployeeQuizModel employeeQuizModel){

        try {

            employeeQuizModel.setScore(0.0);
            int totalScoreAllowedForAllQuestions = 0;

            for (QuizAnswer quizAnswer : employeeQuizModel.getAnswers()){
                employeeQuizModel.setScore(employeeQuizModel.getScore() + quizAnswer.getScore());
            }

            for(QuizQuestionModel quizQuestionModel: quizQuestionRepository.findAllByQuizId(employeeQuizModel.getQuizId())){
                totalScoreAllowedForAllQuestions += quizQuestionModel.getScoreAllowed();
            }

            Optional<QuizModel> quizModelOptional = quizRepository.findById(employeeQuizModel.getQuizId());

            if(quizModelOptional.isPresent()) {
                employeeQuizModel.setScore(
                        Double.valueOf(
                                String.format("%.2f", (employeeQuizModel.getScore() / totalScoreAllowedForAllQuestions) * quizModelOptional.get().getMaximumGradeAllowed())
                        )
                );

                employeeQuizModel.setStatus("Evaluated");
                employeeQuizRepository.save(employeeQuizModel);
            }

        } catch (Exception e){
            System.out.println(e);
        }

    }
}
