package com.hris.HRIS.repository;

import com.hris.HRIS.model.MultimediaModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MultimediaRepository extends MongoRepository<MultimediaModel, String> {
    Optional<List<MultimediaModel>> findAllByUserId(String id);

    Optional<List<MultimediaModel>> findAllByChannelId(String id);

    Optional<List<MultimediaModel>> findAllByChatId(String id);
}
