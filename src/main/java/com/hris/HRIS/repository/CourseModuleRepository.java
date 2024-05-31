package com.hris.HRIS.repository;

import com.hris.HRIS.model.CourseModuleModal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseModuleRepository extends MongoRepository<CourseModuleModal, String> {
    List<CourseModuleModal> findAllByCourseId(String courseId);
}
