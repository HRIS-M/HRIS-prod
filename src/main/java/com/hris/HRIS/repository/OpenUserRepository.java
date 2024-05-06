package com.hris.HRIS.repository;

import com.hris.HRIS.model.OpenUserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OpenUserRepository extends MongoRepository<OpenUserModel, String> {

    Optional<OpenUserModel> findOneByEmail(String email);

    Optional<OpenUserModel> deleteByEmail(String email);
}
