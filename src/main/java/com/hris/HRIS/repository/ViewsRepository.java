package com.hris.HRIS.repository;

import com.hris.HRIS.model.ViewsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ViewsRepository extends MongoRepository<ViewsModel, String> {
    Optional<List<ViewsModel>> findAllByMessageId(String id);
}
