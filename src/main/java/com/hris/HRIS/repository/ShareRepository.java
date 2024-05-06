package com.hris.HRIS.repository;

import com.hris.HRIS.model.ShareModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ShareRepository extends MongoRepository<ShareModel, String> {
    Optional<List<ShareModel>> findAllByMessageId(String id);

    Optional<List<ShareModel>> findAllByUserId(String id);

    Optional<List<ShareModel>> findAllByMultimediaId(String id);
}
