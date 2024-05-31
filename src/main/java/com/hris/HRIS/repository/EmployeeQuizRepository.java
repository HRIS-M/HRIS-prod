package com.hris.HRIS.repository;

import com.hris.HRIS.model.EmployeeQuizModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeQuizRepository extends MongoRepository<EmployeeQuizModel, String> {
    List<EmployeeQuizModel> findAllByEmployeeEmail(String email);
}
