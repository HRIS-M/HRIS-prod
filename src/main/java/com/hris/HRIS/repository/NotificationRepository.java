package com.hris.HRIS.repository;

import com.hris.HRIS.model.NotificationModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NotificationRepository extends MongoRepository<NotificationModel, String> {
    Optional<NotificationModel> deleteAllByUserId(String id);
}
