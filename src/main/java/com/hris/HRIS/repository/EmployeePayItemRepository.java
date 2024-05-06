package com.hris.HRIS.repository;

import com.hris.HRIS.model.EmployeePayItemModel;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeePayItemRepository extends MongoRepository<EmployeePayItemModel, String>{
    List<EmployeePayItemModel> findAllByEmail(String email);
    Optional<EmployeePayItemModel> deleteByEmail(String email);
}
