package com.hris.HRIS.repository;

import com.hris.HRIS.model.MessageModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends MongoRepository<MessageModel, String> {
    Optional<List<MessageModel>> findAllByUserId(String id);

    Optional<List<MessageModel>> findAllByChannelId(String id);

    Optional<List<MessageModel>> findAllByChatId(String id);
}
