package com.hris.HRIS.repository;

import com.hris.HRIS.model.CourseModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<CourseModel, String> {
    
}
