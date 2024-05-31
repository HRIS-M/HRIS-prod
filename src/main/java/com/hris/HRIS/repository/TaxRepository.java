package com.hris.HRIS.repository;

import com.hris.HRIS.model.TaxModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaxRepository extends MongoRepository<TaxModel, String> {
    List<TaxModel> findAllByOrganizationId(String organizationId);
}
