package com.hris.HRIS.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hris.HRIS.model.CourseModel;

public interface CourseRepository extends MongoRepository<CourseModel, String> {
    
}
