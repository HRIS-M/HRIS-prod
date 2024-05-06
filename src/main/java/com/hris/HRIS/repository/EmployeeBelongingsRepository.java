package com.hris.HRIS.repository;

import com.hris.HRIS.model.EmployeeBelongingsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmployeeBelongingsRepository extends MongoRepository<EmployeeBelongingsModel, String> {
    Optional<EmployeeBelongingsModel> findByEmail(String email);

    Optional<EmployeeBelongingsModel> deleteByEmail(String email);
}
