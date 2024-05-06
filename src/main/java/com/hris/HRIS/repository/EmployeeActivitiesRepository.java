package com.hris.HRIS.repository;

import com.hris.HRIS.model.EmployeeActivitiesModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeActivitiesRepository extends MongoRepository<EmployeeActivitiesModel, String> {
}
