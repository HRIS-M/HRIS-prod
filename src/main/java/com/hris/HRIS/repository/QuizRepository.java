package com.hris.HRIS.repository;

import com.hris.HRIS.model.QuizModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuizRepository extends MongoRepository<QuizModel, String> {

     List<QuizModel> findAllByModuleId(String moduleId);
     
}
