package com.hris.HRIS.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hris.HRIS.model.CourseModuleModal;

public interface CourseModuleRepository extends MongoRepository<CourseModuleModal, String> {
    List<CourseModuleModal> findAllByCourseId(String courseId);
}
