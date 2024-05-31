package com.hris.HRIS.repository;

import com.hris.HRIS.model.CourseAssignmentModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseAssignmentRepository extends MongoRepository<CourseAssignmentModel, String> {
    
    List<CourseAssignmentModel> findAllByModuleId(String moduleId);

}
