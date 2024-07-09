package com.hris.HRIS.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hris.HRIS.model.CourseAssignmentModel;

public interface CourseAssignmentRepository extends MongoRepository<CourseAssignmentModel, String> {
    
    List<CourseAssignmentModel> findAllByModuleId(String moduleId);

}
