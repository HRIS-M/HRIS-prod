package com.hris.HRIS.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hris.HRIS.model.QuizQuestionModel;

public interface QuizQuestionRepository extends MongoRepository<QuizQuestionModel, String> {
    List<QuizQuestionModel> findAllByQuizId(String quizId);
}
