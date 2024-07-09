package com.hris.HRIS.repository;

import com.hris.HRIS.model.HolidayModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HolidayRepository extends MongoRepository<HolidayModel, String> {
}
