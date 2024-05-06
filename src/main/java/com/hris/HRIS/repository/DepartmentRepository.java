package com.hris.HRIS.repository;

import com.hris.HRIS.model.DepartmentModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends MongoRepository<DepartmentModel, String> {
    Optional<List<DepartmentModel>> findAllByOrganizationId(String organizationId);
}
