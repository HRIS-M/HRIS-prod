package com.hris.HRIS.repository;

import com.hris.HRIS.model.CommentModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<CommentModel, String> {
    Optional<List<CommentModel>> findAllByUserId(String id);

    Optional<List<CommentModel>> findAllByMultimediaId(String id);

    Optional<List<CommentModel>> findAllByMessageId(String id);
}
