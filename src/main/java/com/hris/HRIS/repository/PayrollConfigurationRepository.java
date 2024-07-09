package com.hris.HRIS.repository;

import com.hris.HRIS.model.PayrollConfigurationModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PayrollConfigurationRepository extends MongoRepository<PayrollConfigurationModel, String> {
    Optional<PayrollConfigurationModel> findByOrganizationId(String organizationId);
}
