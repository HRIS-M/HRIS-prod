package com.hris.HRIS.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hris.HRIS.model.CourseLearningMaterialModal;

public interface CourseLearningMaterialRepository extends MongoRepository<CourseLearningMaterialModal, String> {
    
    List<CourseLearningMaterialModal> findAllByModuleId(String moduleId);
    List<CourseLearningMaterialModal> findAllByAssignmentId(String assignmentId);
}
