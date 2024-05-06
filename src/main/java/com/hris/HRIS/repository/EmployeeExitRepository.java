package com.hris.HRIS.repository;

import com.hris.HRIS.model.EmployeeExitModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmployeeExitRepository extends MongoRepository<EmployeeExitModel, String> {
    Optional<EmployeeExitModel> findByEmail(String email);

    Optional<EmployeeExitModel> deleteByEmail(String email);
}
