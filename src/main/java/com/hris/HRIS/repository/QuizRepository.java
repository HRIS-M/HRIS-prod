package com.hris.HRIS.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hris.HRIS.model.QuizModel;

public interface QuizRepository extends MongoRepository<QuizModel, String> {

     List<QuizModel> findAllByModuleId(String moduleId);
     
}
