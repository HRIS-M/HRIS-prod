package com.hris.HRIS.repository;

import com.hris.HRIS.model.CredentialsModel;
import com.hris.HRIS.model.OpenUserCredentialsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OpenUserCredentialsRepository extends MongoRepository<OpenUserCredentialsModel, String> {

    Optional<OpenUserCredentialsModel> findByEmail(String email);

    Optional<OpenUserCredentialsModel> deleteByEmail(String email);
}
