package com.hris.HRIS.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hris.HRIS.model.EmployeeQuizModel;

public interface EmployeeQuizRepository extends MongoRepository<EmployeeQuizModel, String> {
    List<EmployeeQuizModel> findAllByEmployeeEmail(String email);
}
