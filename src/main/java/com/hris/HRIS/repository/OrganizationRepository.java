package com.hris.HRIS.repository;

import com.hris.HRIS.model.OrganizationModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrganizationRepository extends MongoRepository<OrganizationModel, String> {
    Optional<OrganizationModel> findByEmail(String id);

    void deleteByEmail(String id);
}
