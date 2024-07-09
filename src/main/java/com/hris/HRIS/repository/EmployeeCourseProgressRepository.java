package com.hris.HRIS.repository;

import com.hris.HRIS.model.EmployeeCourseProgressModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeCourseProgressRepository extends MongoRepository<EmployeeCourseProgressModel, String> {
    List<EmployeeCourseProgressModel> findAllByEmployeeEmail(String employeeId);
}
