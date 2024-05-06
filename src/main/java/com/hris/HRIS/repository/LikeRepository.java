package com.hris.HRIS.repository;

import com.hris.HRIS.model.LikeModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends MongoRepository<LikeModel, String> {
    Optional<List<LikeModel>> findAllByMessageId(String id);

    Optional<List<LikeModel>> findAllByUserId(String id);

    Optional<List<LikeModel>> findAllByMultimediaId(String id);
}
