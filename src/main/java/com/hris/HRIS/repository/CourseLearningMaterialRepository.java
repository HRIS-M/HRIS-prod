package com.hris.HRIS.repository;

import com.hris.HRIS.model.CourseLearningMaterialModal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseLearningMaterialRepository extends MongoRepository<CourseLearningMaterialModal, String> {
    
    List<CourseLearningMaterialModal> findAllByModuleId(String moduleId);
    List<CourseLearningMaterialModal> findAllByAssignmentId(String assignmentId);
}
