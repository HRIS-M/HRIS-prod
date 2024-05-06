package com.hris.HRIS.repository;

import com.hris.HRIS.model.EmployeeModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<EmployeeModel, String> {

    Optional<EmployeeModel> findOneByEmail(String email);

    Optional<EmployeeModel> deleteByEmail(String email);
}
