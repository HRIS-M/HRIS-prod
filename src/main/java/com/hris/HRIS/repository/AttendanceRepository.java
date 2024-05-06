package com.hris.HRIS.repository;

import com.hris.HRIS.model.AttendanceModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface AttendanceRepository extends MongoRepository<AttendanceModel, String> {
    Optional<AttendanceModel> findById(String id);

    void deleteById(String id);

}


