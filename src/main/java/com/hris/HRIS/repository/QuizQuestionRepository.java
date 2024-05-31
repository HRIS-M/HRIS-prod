package com.hris.HRIS.repository;

import com.hris.HRIS.model.QuizQuestionModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuizQuestionRepository extends MongoRepository<QuizQuestionModel, String> {
    List<QuizQuestionModel> findAllByQuizId(String quizId);
}
