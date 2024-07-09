package com.hris.HRIS.repository;

import com.hris.HRIS.model.EventModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<EventModel, String> {
}
