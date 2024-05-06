package com.hris.HRIS.repository;

import com.hris.HRIS.model.TransferModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TransferRepository extends MongoRepository<TransferModel, String> {
    Optional<TransferModel> findByEmail(String email);

    Optional<TransferModel> deleteByEmail(String email);
}
