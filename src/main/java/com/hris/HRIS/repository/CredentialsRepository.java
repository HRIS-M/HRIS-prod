package com.hris.HRIS.repository;

import com.hris.HRIS.model.CredentialsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CredentialsRepository extends MongoRepository<CredentialsModel, String> {

    Optional<CredentialsModel> findByEmail(String email);

    Optional<CredentialsModel> deleteByEmail(String email);
}
