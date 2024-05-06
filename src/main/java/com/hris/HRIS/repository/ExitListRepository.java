package com.hris.HRIS.repository;

import com.hris.HRIS.model.ExitListModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ExitListRepository extends MongoRepository<ExitListModel, String> {

    Optional<ExitListModel> findByEmail(String email);

    Optional<ExitListModel> deleteByEmail(String email);
}
