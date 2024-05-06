package com.hris.HRIS.repository;

import com.hris.HRIS.model.ChatModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<ChatModel, String> {
}
