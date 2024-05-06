package com.hris.HRIS.repository;

import com.hris.HRIS.model.ShiftModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShiftRepository extends MongoRepository<ShiftModel, String> {
}
